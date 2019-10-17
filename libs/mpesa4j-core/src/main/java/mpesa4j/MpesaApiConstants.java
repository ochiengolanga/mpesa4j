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
package mpesa4j;

/** This class contains OAuth constants */
public interface MpesaApiConstants {
  String BEARER = "Bearer";
  String BASIC = "Basic";
  String ACCESS_TOKEN = "access_token";
  String CLIENT_ID = "client_id";
  String CLIENT_SECRET = "client_secret";
  String GRANT_TYPE = "grant_type";

  // not OAuth specific
  String ACCEPT_ENCODING_HEADER_NAME = "Accept-Encoding";
  String ACCEPT_TYPE_HEADER_NAME = "Accept-Type";
  String AUTHORIZATION_HEADER_NAME = "Authorization";
  String CACHE_CONTROL_HEADER_NAME = "Cache-Control";
  String CONTENT_TYPE_HEADER_NAME = "Content-Type";
  String USER_AGENT_HEADER_NAME = "User-Agent";
}
