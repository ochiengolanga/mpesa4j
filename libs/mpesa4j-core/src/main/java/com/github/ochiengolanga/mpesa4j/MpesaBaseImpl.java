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

import com.github.ochiengolanga.mpesa4j.auth.Authorization;
import com.github.ochiengolanga.mpesa4j.auth.AuthorizationCache;
import com.github.ochiengolanga.mpesa4j.auth.OAuth2Support;
import com.github.ochiengolanga.mpesa4j.auth.OAuth2Token;
import com.github.ochiengolanga.mpesa4j.config.Configuration;
import com.github.ochiengolanga.mpesa4j.models.exceptions.MpesaApiException;

abstract class MpesaBaseImpl implements MpesaBase, OAuth2Support, java.io.Serializable {
  private static final long serialVersionUID = -7824361938865528554L;
  transient HttpClient http;
  transient AuthorizationCache authCache;
  Configuration conf;
  Authorization auth;

  MpesaBaseImpl(Configuration conf, Authorization auth) {
    this.conf = conf;
    this.auth = auth;
    init();
  }

  public void init() {
    authCache = AuthorizationCache.getInstance();
    http = HttpClientFactory.getInstance(conf.getHttpClientConfiguration());
  }

  @Override
  public Configuration getConfiguration() {
    return this.conf;
  }

  @Override
  public synchronized OAuth2Token getOAuth2Token() throws MpesaApiException {
    return getOAuth2().getOAuth2Token();
  }

  private OAuth2Support getOAuth2() {
    if (auth == null) {
      throw new IllegalStateException("OAuth consumer key/secret combination not supplied");
    }
    return auth;
  }
}
