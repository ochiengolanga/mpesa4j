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
package com.github.ochiengolanga.mpesa4j.transaction.reversal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.responses.BusinessTransactionReversalResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessPaymentRequestReversalTest {

  @BeforeEach
  void init() {}

  @Test
  void businessTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    BusinessTransactionReversalResponse response =
        mpesa.reverseBusinessTransaction(
            "LGR019G3J2", new BigDecimal(10.00), "PaymentRequest reversal request", "");

    assertNotNull(response.getConversationId());
    assertNotNull(response.getOriginatorConversationId());
    assertNotNull(response.getResponseDescription());
    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  }

  @Test
  void nullTransactionId_BusinessTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseBusinessTransaction(
              null, new BigDecimal(10.00), "PaymentRequest reversal request", "");
        });
  }

  @Test
  void emptyTransactionId_BusinessTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseBusinessTransaction(
              "", new BigDecimal(10.00), "PaymentRequest reversal request", "");
        });
  }

  @Test
  void zeroReversibleAmount_BusinessTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseBusinessTransaction(
              "LGR019G3J2", new BigDecimal(0.00), "PaymentRequest reversal request", "");
        });
  }

  @Test
  void negativeReversibleAmount_BusinessTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseBusinessTransaction(
              "LGR019G3J2", new BigDecimal(-10.00), "PaymentRequest reversal request", "");
        });
  }

  @Test
  void nullReversibleAmount_BusinessTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseBusinessTransaction(
              "LGR019G3J2", null, "PaymentRequest reversal request", "");
        });
  }

  @Test
  void nullDescription_BusinessTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseBusinessTransaction("LGR019G3J2", new BigDecimal(10.00), null, "");
        });
  }

  @Test
  void emptyDescription_BusinessTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseBusinessTransaction("LGR019G3J2", new BigDecimal(10.00), "", "");
        });
  }

  @Test
  void nullOccasion_BusinessTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    mpesa.reverseBusinessTransaction(
        "LGR019G3J2", new BigDecimal(10.00), "PaymentRequest reversal request", null);
  }
}
