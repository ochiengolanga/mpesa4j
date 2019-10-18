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
package com.github.ochiengolanga.mpesa4j.auth;

import com.github.ochiengolanga.mpesa4j.models.exceptions.MpesaApiException;

public interface OAuth2Support {
  /**
   * Obtains an OAuth 2 Bearer token.
   *
   * @return OAuth 2 Bearer token
   * @throws MpesaApiException when Mpesa service or network is unavailable, or connecting non-SSL
   *     endpoints.
   * @throws IllegalStateException when Bearer token is already available, or OAuth consumer is not
   *     available.
   * @see <a href="https://developer.safaricom.co.ke/oauth/apis/get/generate-1"></a>
   */
  OAuth2Token getOAuth2Token() throws MpesaApiException;
}
