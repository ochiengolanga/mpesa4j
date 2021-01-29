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
package com.github.ochiengolanga.mpesa4j;

public interface HttpResponseCode {
  // 400	Bad Request
  // 401	Unauthorized
  // 403	Forbidden
  // 404	Not Found
  // 405	Method Not Allowed
  // 406	Not Acceptable ??? You requested a format that isn???t json
  // 429	Too Many Requests
  // 500	Internal Server Error ??? We had a problem with our server. Try again later.
  // 503	Service Unavailable ??? We???re temporarily offline for maintenance. Please try again later.

  int OK = 200;
  int BAD_REQUEST = 400;
  int UNAUTHORIZED = 401;
  int FORBIDDEN = 403;
  int NOT_FOUND = 404;
  int METHOD_NOT_ALLOWED = 405;
  int NOT_ACCEPTABLE = 406;
  int TOO_MANY_REQUESTS = 429;
  int INTERNAL_SERVER_ERROR = 500;
  int SERVICE_UNAVAILABLE = 503;
}
