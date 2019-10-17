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
package mpesa4j.auth;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import mpesa4j.HttpClient;
import mpesa4j.HttpClientFactory;
import mpesa4j.HttpParameter;
import mpesa4j.config.Configuration;
import mpesa4j.models.ApiResource;
import mpesa4j.models.exceptions.MpesaApiException;

@EqualsAndHashCode(callSuper = false)
@ToString
public class Authorization implements OAuth2Support, java.io.Serializable {
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

  @Override
  public OAuth2Token getOAuth2Token() throws MpesaApiException {
    HttpParameter[] params = new HttpParameter[1];
    params[0] = new HttpParameter("grant_type", "client_credentials");

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
  public String getBearerAuthorizationHeader() {
    OAuth2Token ot = authorizationCache.get();
    if (ot != null) {
      return generateBearerAuthenticationString(ot.getAccessToken());
    }
    return null;
  }

  private static String generateBearerAuthenticationString(String accessToken) {
    String encoded = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);
    return "Bearer " + encoded;
  }

  private static String generateBasicAuthenticationString(
      String consumerKey, String consumerSecret) {
    if (consumerKey != null && consumerSecret != null) {
      String credentials =
          URLEncoder.encode(consumerKey, StandardCharsets.UTF_8)
              + ":"
              + URLEncoder.encode(consumerSecret, StandardCharsets.UTF_8);

      return "Basic "
          + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
    return null;
  }
}
