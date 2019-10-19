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

import com.github.ochiengolanga.mpesa4j.models.ApiResource;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
public class InstantPaymentRequest extends ApiResource {

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

  /**
   * Unique command for each transaction type e.g. CustomerPayBillOnline and CustomerBuyGoodsOnline
   */
  @SerializedName("TransactionType")
  String transactionType;

  /** The amount being transacted */
  @SerializedName("Amount")
  String amount;

  /**
   * The Debit party of the transaction/the party paying out in the transaction, hereby the phone
   * number of the customer.
   */
  @SerializedName("PartyA")
  String customerPhoneNumber;

  /** Same as PartyA. */
  @SerializedName("PhoneNumber")
  String phoneNumber;

  /**
   * The credit party of the transaction/the party being paid in the transaction, hereby being the
   * shortcode of the organization. This is the same value as the Business Shortcode
   */
  @SerializedName("PartyB")
  String initiatorShortCode;

  /**
   * This is the endpoint where you want the results of the transaction delivered. Same rules for
   * Register URL API callbacks apply
   */
  @SerializedName("CallBackURL")
  String callbackUrl;

  /**
   * This is the value the customer would have put as the account number on their phone if they had
   * performed the transaction via phone.
   */
  @SerializedName("AccountReference")
  String accountReference;

  /** Short description of the transaction. Optional, but element must be present. */
  @SerializedName("TransactionDesc")
  String transactionDescription;
}
