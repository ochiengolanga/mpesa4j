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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** A data interface representing B2C PayBusiness API response */
@SuppressWarnings("SameNameButDifferent")
@AllArgsConstructor
@Getter
@ToString
public final class InstantPaymentRequestResponse implements java.io.Serializable {
  private static final long serialVersionUID = 7092906238192790921L;

  private String merchantRequestId;
  private String checkoutRequestId;
  private String responseCode;
  private String responseDescription;
  private String customerMessage;
}
