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
package com.github.ochiengolanga.mpesa4j.models.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/** A data interface representing a response from M-Pesa API */
@Getter
@ToString
public class MpesaResponse implements MpesaResponseCode, java.io.Serializable {
  private static final long serialVersionUID = 7092906238192790921L;

  @SerializedName("ConversationID")
  private String conversationId;

  @SerializedName(value = "OriginatorConversationID", alternate = "OriginatorCoversationID")
  private String originatorConversationId;

  @SerializedName("ResponseCode")
  private String responseCode;

  @SerializedName("ResponseDescription")
  private String responseDescription;

  @SerializedName("MerchantRequestID")
  private String merchantRequestId;

  @SerializedName("CheckoutRequestID")
  private String checkoutRequestId;

  @SerializedName("CustomerMessage")
  private String customerMessage;

  @SerializedName("ResultCode")
  private String resultCode;

  @SerializedName("ResultDesc")
  private String resultDescription;
}
