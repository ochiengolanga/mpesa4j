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

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;

public class AuthorizationCache implements java.io.Serializable {
  private static final long serialVersionUID = -403500272719330534L;
  private static final String OAUTH2_TOKEN = "oauth2_token";
  private final Cache<String, OAuth2Token> cache;

  private static AuthorizationCache ac = new AuthorizationCache();

  public static AuthorizationCache getInstance() {
    return ac;
  }

  private AuthorizationCache() {
    cache = Caffeine.newBuilder().expireAfterWrite(3599, TimeUnit.SECONDS).maximumSize(1).build();
  }

  public boolean isEmpty() {
    return cache.getIfPresent(OAUTH2_TOKEN) == null;
  }

  public void put(OAuth2Token token) {
    cache.put(OAUTH2_TOKEN, token);
  }

  public OAuth2Token get() {
    return cache.getIfPresent(OAUTH2_TOKEN);
  }
}
