/*
 * Copyright 2019-2020 Daniel Ochieng' Olanga.
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

import com.github.ochiengolanga.mpesa4j.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.ApiResource;
import com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponse;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpClientBase implements HttpClient, java.io.Serializable {
  private static final long serialVersionUID = -8016974810651763053L;
  protected final HttpClientConfiguration httpClientConfiguration;

  private final Map<String, String> requestHeaders;

  public HttpClientBase(HttpClientConfiguration conf) {
    this.httpClientConfiguration = conf;
    requestHeaders = new HashMap<>();
    requestHeaders.put(
        MpesaApiConstants.USER_AGENT_HEADER_NAME,
        "mpesa4j https://github.com/ochiengolanga/mpesa4j");
    requestHeaders.put(MpesaApiConstants.ACCEPT_ENCODING_HEADER_NAME, "gzip");
    requestHeaders.put(MpesaApiConstants.ACCEPT_TYPE_HEADER_NAME, "application/json");
    requestHeaders.put(MpesaApiConstants.CACHE_CONTROL_HEADER_NAME, "no-cache");
  }

  abstract MpesaResponse handleRequest(HttpRequest req) throws MpesaApiException;

  abstract <T> T handleOAuthRequest(HttpRequest req, Class<T> clazz) throws MpesaApiException;

  @Override
  public MpesaResponse request(
      ApiResource.RequestMethod method,
      String url,
      String authorizationHeader,
      HttpParameter[] httpParameters)
      throws MpesaApiException {
    requestHeaders.put(MpesaApiConstants.AUTHORIZATION_HEADER_NAME, authorizationHeader);
    return handleRequest(new HttpRequest(method, url, requestHeaders, httpParameters));
  }

  @Override
  public <T> T oauthRequest(
      ApiResource.RequestMethod method,
      String url,
      String authorizationHeader,
      HttpParameter[] httpParameters,
      Class<T> clazz)
      throws MpesaApiException {
    requestHeaders.put(MpesaApiConstants.AUTHORIZATION_HEADER_NAME, authorizationHeader);
    return handleOAuthRequest(new HttpRequest(method, url, requestHeaders, httpParameters), clazz);
  }
}
