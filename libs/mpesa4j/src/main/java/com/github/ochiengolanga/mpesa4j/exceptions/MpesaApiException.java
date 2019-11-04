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
package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are failed.<br>
 * In case the M-Pesa API returned HTTP error code, you can get the HTTP status code using
 * getStatusCode() method.
 */
public class MpesaApiException extends Exception {
  private static final long serialVersionUID = 6006561839051121336L;

  public MpesaApiException(String message) {
    this(message, null);
  }

  public MpesaApiException(String message, Throwable cause) {
    super(message, cause);
  }
}
