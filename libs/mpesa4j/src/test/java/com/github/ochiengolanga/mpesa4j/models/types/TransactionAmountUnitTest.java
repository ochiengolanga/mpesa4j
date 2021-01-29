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
package com.github.ochiengolanga.mpesa4j.models.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionAmountUnitTest {
  @BeforeEach
  void init() {}

  @Test
  void give_PositiveAmount_Return_TransactionAmount() {
    TransactionAmount from = TransactionAmount.of(new BigDecimal("10"));

    assertNotNull(from);
    assertEquals("10", from.getValue().toString());
  }

  @Test
  void give_PositiveWithADecimalAmount_Return_TransactionAmount() {
    TransactionAmount from = TransactionAmount.of(new BigDecimal("10.00"));

    assertNotNull(from);
    assertEquals("10", from.getValue().toString());
  }

  @Test
  void given_NegativeAmount_ShouldThrow_IllegalArgumentException() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> TransactionAmount.of(new BigDecimal("-10.00")));
  }

  @Test
  void given_ZeroAmount_ShouldThrow_IllegalArgumentException() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> TransactionAmount.of(new BigDecimal("0.00")));
  }
}
