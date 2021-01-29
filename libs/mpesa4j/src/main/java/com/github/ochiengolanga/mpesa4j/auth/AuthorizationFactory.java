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

import com.github.ochiengolanga.mpesa4j.config.Configuration;

/** A static factor class for Authorization */
public final class AuthorizationFactory {
  public static Authorization getInstance(Configuration conf) {
    Authorization auth = null;
    String consumerKey = conf.getConsumerKey();
    String consumerSecret = conf.getConsumerSecret();

    if (consumerKey != null && consumerSecret != null) {
      auth = new Authorization(conf);
    }
    return auth;
  }
}
