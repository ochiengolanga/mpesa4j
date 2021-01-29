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
package com.github.ochiengolanga.mpesa4j.api;

import com.github.ochiengolanga.mpesa4j.models.enums.DefaultAction;
import com.github.ochiengolanga.mpesa4j.models.responses.CallbackUrlsRegistrationResponse;
import com.github.ochiengolanga.mpesa4j.models.types.ConfirmationUrl;
import com.github.ochiengolanga.mpesa4j.models.types.ValidationUrl;
import lombok.NonNull;

public interface ConfigurationResource {
  /**
   * Allows you to register callback URLs i.e. Validation and Confirmation URL under Consumer to
   * Business (C2B) APIs. It is the first half of the C2B API for receiving payment notifications to
   * your paybill / till number. <br>
   * see https://developer.safaricom.co.ke/docs?java#b2c-api
   *
   * @param defaultAction of type {@link DefaultAction}
   * @param validationUrl of type {@link String}
   * @param confirmationUrl of type {@link String}
   * @return {@link CallbackUrlsRegistrationResponse}
   */
  CallbackUrlsRegistrationResponse registerCallbackUrls(
      @NonNull DefaultAction defaultAction,
      @NonNull ValidationUrl validationUrl,
      @NonNull ConfirmationUrl confirmationUrl);
}
