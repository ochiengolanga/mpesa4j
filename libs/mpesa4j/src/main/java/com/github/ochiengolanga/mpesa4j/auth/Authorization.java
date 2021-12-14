/*
 * Copyright 2019-2021 Daniel Ochieng' Olanga.
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
package com.github.ochiengolanga.mpesa4j.auth;

import com.github.ochiengolanga.mpesa4j.HttpClient;
import com.github.ochiengolanga.mpesa4j.HttpClientFactory;
import com.github.ochiengolanga.mpesa4j.HttpParameter;
import com.github.ochiengolanga.mpesa4j.MpesaApiConstants;
import com.github.ochiengolanga.mpesa4j.config.Configuration;
import com.github.ochiengolanga.mpesa4j.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.ApiResource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class Authorization implements java.io.Serializable {
  private static final long serialVersionUID = -2895232598422218647L;
  private final Configuration conf;
  private final HttpClient http;
  private final AuthorizationCache authorizationCache;
  private String consumerKey;
  private String consumerSecret;

  public Authorization(Configuration conf) {
    this.conf = conf;
    this.consumerKey = conf.getConsumerKey() != null ? conf.getConsumerKey() : "";
    this.consumerSecret = conf.getConsumerSecret() != null ? conf.getConsumerSecret() : "";

    http = HttpClientFactory.getInstance(conf.getHttpClientConfiguration());
    authorizationCache = AuthorizationCache.getInstance();
  }

  private synchronized OAuth2Token getOAuth2Token() throws MpesaApiException {
    HttpParameter[] params = new HttpParameter[1];
    params[0] =
        new HttpParameter(MpesaApiConstants.GRANT_TYPE, MpesaApiConstants.CLIENT_CREDENTIALS);

    OAuth2Token oAuth2Token =
        executeOAuthRequest(
            ApiResource.RequestMethod.GET, conf.getOAuth2TokenUrl(), params, OAuth2Token.class);

    authorizationCache.put(oAuth2Token);

    return oAuth2Token;
  }

  private <T> T executeOAuthRequest(
      ApiResource.RequestMethod method, String url, HttpParameter[] parameters, Class<T> clazz)
      throws MpesaApiException {
    return http.oauthRequest(method, url, getBasicAuthorizationHeader(), parameters, clazz);
  }

  /**
   * Returns Authorization HTTP header either Basic or Bearer http authorization headers
   *
   * @return String
   */
  public String getBasicAuthorizationHeader() {
    return generateBasicAuthenticationString(consumerKey, consumerSecret);
  }

  /**
   * Returns Authorization HTTP header either Basic or Bearer http authorization headers
   *
   * @return String
   */
  public String getBearerAuthorizationHeader() throws MpesaApiException {
    if (authorizationCache.isEmpty()) {
      this.getOAuth2Token();
    }

    OAuth2Token ot2 = authorizationCache.get();
    return generateBearerAuthenticationString(ot2.getAccessToken());
  }

  private static String generateBearerAuthenticationString(String accessToken) {
    String encoded = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);
    return MpesaApiConstants.BEARER + " " + encoded;
  }

  private static String generateBasicAuthenticationString(
      String consumerKey, String consumerSecret) {
    if (consumerKey != null && consumerSecret != null) {
      String credentials =
          URLEncoder.encode(consumerKey, StandardCharsets.UTF_8)
              + ":"
              + URLEncoder.encode(consumerSecret, StandardCharsets.UTF_8);

      return MpesaApiConstants.BASIC
          + " "
          + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
    return null;
  }
}
