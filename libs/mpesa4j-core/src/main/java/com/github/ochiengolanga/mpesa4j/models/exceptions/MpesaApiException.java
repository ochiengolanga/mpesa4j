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
package com.github.ochiengolanga.mpesa4j.models.exceptions;

import com.github.ochiengolanga.mpesa4j.HttpResponseCode;

/**
 * An exception class that will be thrown when M-Pesa API calls are failed.<br>
 * In case the M-Pesa API returned HTTP error code, you can get the HTTP status code using
 * getStatusCode() method.
 */
public class MpesaApiException extends Exception implements HttpResponseCode {
  private static final long serialVersionUID = 6006561839051121336L;

  private int statusCode = -1;
  private int errorCode = -1;
  private String errorMessage = null;

  public MpesaApiException(String message) {
    this(message, null);
  }

  public MpesaApiException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public String getMessage() {
    StringBuilder value = new StringBuilder();
    if (errorMessage != null && errorCode != -1) {
      value.append("message - ").append(errorMessage).append("\n");
      value.append("code - ").append(errorCode).append("\n");
    } else {
      value.append(super.getMessage());
    }
    if (statusCode != -1) {
      return getCause(statusCode) + "\n" + value.toString();
    } else {
      return value.toString();
    }
  }

  private static String getCause(int statusCode) {
    String cause;
    // https://developer.safaricom.co.ke/docs#errors
    switch (statusCode) {
      case BAD_REQUEST:
        cause = "The request was invalid. An accompanying error message will explain why.";
        break;
      case UNAUTHORIZED:
        cause =
            "Authentication credentials (https://developer.safaricom.co.ke/docs#authentication) were missing or incorrect. Ensure that you have set valid consumer key/secret and the system clock is in sync.";
        break;
      case FORBIDDEN:
        cause =
            "The request is understood, but it has been refused. An accompanying error message will explain why.";
        break;
      case NOT_FOUND:
        cause =
            "The URI requested is invalid or the resource requested. Also returned when the requested format is not supported by the requested method.";
        break;
      case METHOD_NOT_ALLOWED:
        cause = "The request is not allowed.";
        break;
      case NOT_ACCEPTABLE:
        cause = "The request is not acceptable. Probably requested a format that is not json.";
        break;
      case TOO_MANY_REQUESTS:
        cause =
            "Returned when a request cannot be served due to the application's rate limit having been exhausted for the resource.";
        break;
      case INTERNAL_SERVER_ERROR:
        cause =
            "Something is broken. Please post to the group (https://developer.safaricom.co.ke/) so the Safaricom Daraja team can investigate.";
        break;
      case SERVICE_UNAVAILABLE:
        cause = "The M-Pesa servers are offline for maintenance. Try again later.";
        break;
      default:
        cause = "";
    }
    return statusCode + ":" + cause;
  }
}
