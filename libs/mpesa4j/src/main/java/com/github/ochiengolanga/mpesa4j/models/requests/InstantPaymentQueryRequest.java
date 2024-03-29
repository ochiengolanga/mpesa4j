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
package com.github.ochiengolanga.mpesa4j.models.requests;

import com.github.ochiengolanga.mpesa4j.models.ApiResource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("SameNameButDifferent")
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
public final class InstantPaymentQueryRequest extends ApiResource {
  /** This is the shortcode of the organization initiating the request and expecting the payment. */
  @SerializedName("BusinessShortCode")
  String businessShortCode;

  /**
   * This is the Base64-encoded value of the concatenation of the Shortcode + LNM Passkey +
   * Timestamp, e.g. given the test values above, and using a timestamp of 20191002052917, the
   * encoded password will be
   */
  @SerializedName("Password")
  String password;

  /** format YYYMMDDHHmmss. */
  @SerializedName("Timestamp")
  String timestamp;

  /** Checkout RequestID from InstantPaymentRequest */
  @SerializedName("CheckoutRequestID")
  String paymentId;
}
