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

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public final class Occasion {
  private final String value;

  private Occasion(String value) {
    this.value = value;
  }

  public static Occasion of(@NonNull String value) {
    Preconditions.checkEmptyString(value, "Invalid occasion. Empty or null.");

    if (value.length() > 100) {
      throw new IllegalArgumentException(
          "Invalid character length. Maximum number of characters allowed is 100.");
    }

    return new Occasion(value);
  }

  public static Occasion none() {
    return new Occasion("");
  }
}
