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
package com.github.ochiengolanga.mpesa4j.models.requests;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import com.github.ochiengolanga.mpesa4j.models.ApiResource;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
public class PaymentRequest extends ApiResource {

  /** This is the credential/username used to authenticate the transaction request. */
  @SerializedName("InitiatorName")
  String initiatorName;

  /** Organization’s shortcode initiating the transaction. */
  @SerializedName("PartyA")
  String initiatorShortCode;

  /**
   * Base64 encoded string of the B2C short code and password, which is encrypted using M-Pesa
   * public key and validates the transaction on M-Pesa Core system.
   */
  @SerializedName("SecurityCredential")
  String credentials;

  /**
   * Unique command for each transaction type e.g. SalaryPayment, BusinessPayment, PromotionPayment
   */
  @SerializedName("CommandID")
  String transactionType;

  /** The amount being transacted */
  @SerializedName("Amount")
  String amount;

  /** Phone number receiving the transaction */
  @SerializedName("PartyB")
  String destination;

  /** Comments that are sent along with the transaction. */
  @SerializedName("Remarks")
  String description;

  /** The timeout end-point that receives a timeout response. */
  @SerializedName("QueueTimeOutURL")
  String queueTimeoutUrl;

  /** The end-point that receives the response of the transaction */
  @SerializedName("ResultURL")
  String resultUrl;

  /** Optional field */
  @SerializedName("Occasion")
  String occasion;
}
