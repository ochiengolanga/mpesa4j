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
package com.github.ochiengolanga.mpesa4j.transaction.reversal;

import static org.junit.jupiter.api.Assertions.*;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.responses.BusinessTransactionReversalResponse;
import com.github.ochiengolanga.mpesa4j.models.types.Description;
import com.github.ochiengolanga.mpesa4j.models.types.Occasion;
import com.github.ochiengolanga.mpesa4j.models.types.TransactionAmount;
import com.github.ochiengolanga.mpesa4j.models.types.TransactionId;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessPaymentRequestReversalTest {

  @BeforeEach
  void init() {}

  @Test
  void businessTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    BusinessTransactionReversalResponse response =
        mpesa.reverseBusinessTransaction(
            TransactionId.of("LGR019G3J2"),
            TransactionAmount.of(new BigDecimal("1000")),
            Description.of("PaymentRequest reversal request"),
            Occasion.none());

    assertNotNull(response.getConversationId());
    assertNotNull(response.getOriginatorConversationId());
    assertNotNull(response.getResponseDescription());
    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  }

  @Test
  void nullTransactionId_BusinessTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseBusinessTransaction(
                null,
                TransactionAmount.of(new BigDecimal("100.00")),
                Description.of("PaymentRequest reversal request"),
                Occasion.none()));
  }

  @Test
  void emptyTransactionId_BusinessTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseBusinessTransaction(
                TransactionId.of(""),
                TransactionAmount.of(new BigDecimal("100.00")),
                Description.of("PaymentRequest reversal request"),
                Occasion.none()));
  }

  @Test
  void zeroReversibleAmount_BusinessTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseBusinessTransaction(
                TransactionId.of("LGR019G3J2"),
                TransactionAmount.of(new BigDecimal("0.00")),
                Description.of("PaymentRequest reversal request"),
                Occasion.none()));
  }

  @Test
  void negativeReversibleAmount_BusinessTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseBusinessTransaction(
                TransactionId.of("LGR019G3J2"),
                TransactionAmount.of(new BigDecimal("-10.00")),
                Description.of("PaymentRequest reversal request"),
                Occasion.none()));
  }

  @Test
  void nullReversibleAmount_BusinessTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseBusinessTransaction(
                TransactionId.of("LGR019G3J2"),
                null,
                Description.of("PaymentRequest reversal request"),
                Occasion.none()));
  }

  @Test
  void nullDescription_BusinessTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseBusinessTransaction(
                TransactionId.of("LGR019G3J2"),
                TransactionAmount.of(new BigDecimal("100.00")),
                null,
                Occasion.none()));
  }

  @Test
  void emptyDescription_BusinessTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseBusinessTransaction(
                TransactionId.of("LGR019G3J2"),
                TransactionAmount.of(new BigDecimal("100.00")),
                Description.of(""),
                Occasion.none()));
  }

  @Test
  void nullOccasion_BusinessTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    mpesa.reverseBusinessTransaction(
        TransactionId.of("LGR019G3J2"),
        TransactionAmount.of(new BigDecimal("100.00")),
        Description.of("PaymentRequest reversal request"),
        null);
  }
}
