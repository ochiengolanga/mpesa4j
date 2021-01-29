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
package com.github.ochiengolanga.mpesa4j.models.types;

import com.github.ochiengolanga.mpesa4j.util.Preconditions;
import lombok.*;

/**
 * This is the customer mobile number to receive the amount. The number should have the country code
 * (254) without the plus sign.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public final class PhoneNumber {
  private final String value;

  private PhoneNumber(String value) {
    this.value = value;
  }

  public static PhoneNumber of(@NonNull String value) {
    Preconditions.checkEmptyString(value, "Invalid phone number. Empty or null");

    if (value.length() < 12) {
      throw new IllegalArgumentException("Invalid phone number. " + value);
    }

    if (!value.startsWith("254")) {
      throw new IllegalArgumentException("Invalid phone number. " + value);
    }

    return new PhoneNumber(value);
  }
}
