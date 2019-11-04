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

import com.github.ochiengolanga.mpesa4j.config.ConfigurationContext;
import com.github.ochiengolanga.mpesa4j.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.ApiResource;
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
  <T> T handleRequest(HttpRequest req, Class<T> clazz) throws MpesaApiException {
    return staticRequest(
        req,
        clazz,
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

  private static <T> T staticRequest(
      HttpRequest req, Class<T> clazz, int connectionTimeout, int readTimeout)
      throws MpesaApiException {
    long requestStartMs = System.currentTimeMillis();

    HttpResponse response =
        rawRequest(
            req.getMethod(),
            req.getUrl(),
            req.getRequestHeaders(),
            req.getParameters(),
            connectionTimeout,
            readTimeout);

    long requestDurationMs = System.currentTimeMillis() - requestStartMs;
    String responseBody = response.getBody();
    int responseCode = response.getStatusCode();

    T resource = null;
    try {
      resource = ApiResource.GSON.fromJson(response.getBody(), clazz);
    } catch (JsonSyntaxException e) {
      raiseMalformedJsonError(responseBody, responseCode);
    }

    System.out.println("Request duration in ms " + requestDurationMs);

    return resource;
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
      handleOAuthError(responseBody, responseCode);
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

      requestBuilder.header(MpesaApiConstants.CONTENT_TYPE_HEADER_NAME, "application/json");
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

  private static void handleOAuthError(String responseBody, int responseCode)
      throws MpesaApiException {
    throw new MpesaApiException(
        String.format(
            "Unable to authenticate: %s. (HTTP response code was %d)", responseBody, responseCode));
  }

  private static void raiseMalformedJsonError(String responseBody, int responseCode)
      throws MpesaApiException {
    throw new MpesaApiException(
        String.format(
            "Invalid response object from API: %s. (HTTP response code was %d)",
            responseBody, responseCode));
  }
}
