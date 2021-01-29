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
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public final class TransactionAmount {
  private final BigDecimal value;

  private TransactionAmount(BigDecimal value) {
    this.value = value;
  }

  public static TransactionAmount of(@NonNull BigDecimal value) {
    Preconditions.checkPositiveNumber(
        value, "Invalid transaction amount. Must be greater than zero.");
    return new TransactionAmount(value.setScale(0, RoundingMode.FLOOR));
  }
}
