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
public class AccountBalanceRequest extends ApiResource {
  /** This is the credential/username used to authenticate the transaction request. */
  @SerializedName("Initiator")
  String initiatorName;

  /**
   * Base64 encoded string of the M-Pesa short code and password, which is encrypted using M-Pesa
   * public key and validates the transaction on M-Pesa Core system.
   */
  @SerializedName("SecurityCredential")
  String credentials;

  /** Unique command for each transaction type, possible values are: TransactionReversal. */
  @SerializedName("CommandID")
  String transactionType;

  /** This is the shortcode of the organization initiating the balance request. */
  @SerializedName("PartyA")
  String businessShortCode;

  /** Type of organization receiving the transaction. */
  @SerializedName("IdentifierType")
  String identifierType;

  /** The path that stores information of time out transaction. */
  @SerializedName("QueueTimeOutURL")
  String queueTimeoutUrl;

  /** The path that stores information of transaction. */
  @SerializedName("ResultURL")
  String resultUrl;

  /** Comments that are sent along with the transaction. */
  @SerializedName("Remarks")
  String description;
}
