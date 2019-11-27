/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2019 Daniel Ochieng' Olanga.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.ochiengolanga.mpesa4j;

import static com.github.ochiengolanga.mpesa4j.HttpResponseCode.*;
import static io.vavr.API.*;

import com.github.ochiengolanga.mpesa4j.config.ConfigurationContext;
import com.github.ochiengolanga.mpesa4j.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.ApiResource;
import com.github.ochiengolanga.mpesa4j.models.responses.MpesaErrorResponse;
import com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponse;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

class HttpClientImpl extends HttpClientBase implements java.io.Serializable {
  private static final long serialVersionUID = -403500272719330534L;

  public HttpClientImpl() {
    super(ConfigurationContext.getInstance().getHttpClientConfiguration());
  }

  public HttpClientImpl(HttpClientConfiguration conf) {
    super(conf);
  }

  private static final Map<HttpClientConfiguration, HttpClient> instanceMap = new HashMap<>(1);

  public static HttpClient getInstance(HttpClientConfiguration conf) {
    HttpClient client = instanceMap.get(conf);
    if (null == client) {
      client = new HttpClientImpl(conf);
      instanceMap.put(conf, client);
    }
    return client;
  }

  @Override
  MpesaResponse handleRequest(HttpRequest req) throws MpesaApiException {
    return staticRequest(
        req,
        httpClientConfiguration.getHttpConnectionTimeout(),
        httpClientConfiguration.getHttpReadTimeout());
  }

  @Override
  <T> T handleOAuthRequest(HttpRequest req, Class<T> clazz) throws MpesaApiException {
    return staticOAuthRequest(
        req,
        clazz,
        httpClientConfiguration.getHttpConnectionTimeout(),
        httpClientConfiguration.getHttpReadTimeout());
  }

  private static MpesaResponse staticRequest(
      HttpRequest req, int connectionTimeout, int readTimeout) throws MpesaApiException {
    long requestStartMs = System.currentTimeMillis();

    HttpResponse res =
        rawRequest(
            req.getMethod(),
            req.getUrl(),
            req.getRequestHeaders(),
            req.getParameters(),
            connectionTimeout,
            readTimeout);

    long requestDurationMs = System.currentTimeMillis() - requestStartMs;

    System.out.println("Request duration in ms " + requestDurationMs);

    String responseBody = res.getBody();
    int responseCode = res.getStatusCode();

    if (responseCode < 200 || responseCode >= 300) {
      handleError(responseBody, responseCode);
    }

    MpesaResponse mpesaResponse = null;
    try {
      mpesaResponse = ApiResource.GSON.fromJson(res.getBody(), MpesaResponse.class);
    } catch (JsonSyntaxException e) {
      raiseMalformedJsonError(responseBody, responseCode);
    }

    return mpesaResponse;
  }

  private static <T> T staticOAuthRequest(
      HttpRequest req, Class<T> clazz, int httpConnectionTimeout, int httpReadTimeout)
      throws MpesaApiException {
    HttpResponse response =
        rawRequest(
            req.getMethod(),
            req.getUrl(),
            req.getRequestHeaders(),
            req.getParameters(),
            httpConnectionTimeout,
            httpReadTimeout);
    String responseBody = response.getBody();
    int responseCode = response.getStatusCode();

    if (responseCode < 200 || responseCode >= 300) {
      handleOAuthError(responseBody);
    }

    T resource = null;
    try {
      resource = ApiResource.GSON.fromJson(response.getBody(), clazz);
    } catch (JsonSyntaxException e) {
      raiseMalformedJsonError(responseBody, responseCode);
    }

    return resource;
  }

  private static HttpResponse rawRequest(
      ApiResource.RequestMethod method,
      String url,
      Map<String, String> requestHeaders,
      HttpParameter[] parameters,
      int connectionTimeout,
      int readTimeout)
      throws MpesaApiException {
    HttpResponse res;

    java.net.http.HttpClient httpClient =
        java.net.http.HttpClient.newBuilder()
            .version(java.net.http.HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofMillis(connectionTimeout))
            .build();

    java.net.http.HttpRequest.Builder requestBuilder =
        java.net.http.HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(Duration.ofMillis(readTimeout));

    if (requestHeaders != null) {
      for (String key : requestHeaders.keySet()) {
        requestBuilder.header(key, requestHeaders.get(key));
      }
    }

    if (method == ApiResource.RequestMethod.POST) {
      byte[] bytes = parameters[0].getValue().getBytes(StandardCharsets.UTF_8);

      requestBuilder.header(
          MpesaApiConstants.CONTENT_TYPE_HEADER_NAME, MpesaApiConstants.CONTENT_TYPE_JSON);
      requestBuilder.POST(java.net.http.HttpRequest.BodyPublishers.ofByteArray(bytes));
    } else {
      requestBuilder.GET();
    }

    java.net.http.HttpRequest httpRequest = requestBuilder.build();

    try {
      java.net.http.HttpResponse<String> httpResponse =
          httpClient.send(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
      res = new HttpResponse(httpResponse.statusCode(), httpResponse.body());
    } catch (InterruptedException | IOException e) {
      throw new MpesaApiException(e.getMessage(), e);
    }

    return res;
  }

  private static void handleError(String responseBody, int statusCode) throws MpesaApiException {
    MpesaErrorResponse errorResponse =
        ApiResource.GSON.fromJson(responseBody, MpesaErrorResponse.class);

    String message;

    if (errorResponse.getErrorMessage() != null && !errorResponse.getErrorMessage().isEmpty()) {
      message = errorResponse.getErrorMessage();
    } else {
      message =
          Match(statusCode)
              .of(
                  Case($(BAD_REQUEST), "Oops!"),
                  Case(
                      $(UNAUTHORIZED),
                      "Invalid or missing authentication credentials. Ensure that you have set valid consumer key/secret and the system clock is in sync."),
                  Case($(FORBIDDEN), "The request is understood, but it has been refused."),
                  Case(
                      $(NOT_FOUND),
                      "The URI requested is invalid or the resource requested. Also returned when the requested format is not supported by the requested method."),
                  Case($(METHOD_NOT_ALLOWED), "The request is not allowed."),
                  Case(
                      $(NOT_ACCEPTABLE),
                      "The request is not acceptable. Probably requested a format that is not json."),
                  Case(
                      $(TOO_MANY_REQUESTS),
                      "Returned when a request cannot be served due to the application's rate limit having been exhausted for the resource."),
                  Case(
                      $(INTERNAL_SERVER_ERROR),
                      "Something is broken. Please post to the group (https://developer.safaricom.co.ke/) so the Safaricom Daraja team can investigate."),
                  Case($(SERVICE_UNAVAILABLE), "Service unavailable. Try again later."),
                  Case($(), ""));
    }

    throw new MpesaApiException(String.format("%s", message));
  }

  private static void handleOAuthError(String responseBody) throws MpesaApiException {
    throw new MpesaApiException(
        String.format(
            "Unable to authenticate: %s",
            responseBody.isEmpty() ? "Invalid credentials" : responseBody));
  }

  private static void raiseMalformedJsonError(String responseBody, int responseCode)
      throws MpesaApiException {
    throw new MpesaApiException(
        String.format(
            "Invalid response object from API: %s. (HTTP response code was %d)",
            responseBody, responseCode));
  }
}
