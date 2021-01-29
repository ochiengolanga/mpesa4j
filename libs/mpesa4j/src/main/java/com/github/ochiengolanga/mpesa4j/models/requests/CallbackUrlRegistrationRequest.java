/*
 * Copyright 2019-2020 Daniel Ochieng' Olanga.
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
import java.net.URL;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
public final class CallbackUrlRegistrationRequest extends ApiResource {
  /**
   * This is your C2B-enabled paybill number/till number, which you expect to receive payments
   * notifications about.
   */
  @SerializedName("ShortCode")
  String shortCode;

  /**
   * This is the default action value that determines what M-Pesa will do in the scenario that your
   * Validation endpoint is unreachable or is unable to respond on time. Only two values are
   * allowed: Completed or Cancelled. Completed means M-Pesa will automatically complete your
   * transaction, whereas Cancelled means M-Pesa will automatically cancel the transaction.
   */
  @SerializedName("ResponseType")
  String callbackType;

  @SerializedName("ConfirmationURL")
  URL confirmationUrl;

  @SerializedName("ValidationURL")
  URL validationUrl;
}
