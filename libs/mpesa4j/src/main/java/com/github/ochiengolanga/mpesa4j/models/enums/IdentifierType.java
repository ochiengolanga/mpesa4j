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
package com.github.ochiengolanga.mpesa4j.models.enums;

import lombok.Getter;

/**
 * Identifier types - both sender and receiver - identify an M-Pesa transaction’s sending and
 * receiving party as either a shortcode, a till number or a MSISDN (phone number). There are three
 * identifier types that can be used with M-Pesa APIs. <br>
 * https://developer.safaricom.co.ke/docs#identifier-types
 */
@Getter
public enum IdentifierType {
  MSISDN("1"),
  TILL_NUMBER("2"),
  SHORTCODE("4");

  private final String identifierType;

  IdentifierType(String identifierType) {
    this.identifierType = identifierType;
  }
}
