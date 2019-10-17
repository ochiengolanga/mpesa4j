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
package mpesa4j.transaction.reversal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import mpesa4j.Mpesa;
import mpesa4j.MpesaFactory;
import mpesa4j.models.exceptions.MpesaApiException;
import mpesa4j.models.responses.CustomerTransactionReversalResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerPaymentRequestReversalTest {

  @BeforeEach
  void init() {}

  @Test
  void customerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    CustomerTransactionReversalResponse response =
        mpesa.reverseCustomerTransaction(
            "254708374149",
            "LGR019G3J2",
            new BigDecimal(10.00),
            "PaymentRequest reversal request",
            "");

    assertNotNull(response.getConversationId());
    assertNotNull(response.getOriginatorConversationId());
    assertNotNull(response.getResponseDescription());
    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  }

  @Test
  void nullCustomerPhoneNumber_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseCustomerTransaction(
              null, "LGR019G3J2", new BigDecimal(10.00), "PaymentRequest reversal request", "");
        });
  }

  @Test
  void emptyCustomerPhoneNumber_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseCustomerTransaction(
              "", "LGR019G3J2", new BigDecimal(10.00), "PaymentRequest reversal request", "");
        });
  }

  @Test
  void nullTransactionId_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseCustomerTransaction(
              "254708374149", null, new BigDecimal(10.00), "PaymentRequest reversal request", "");
        });
  }

  @Test
  void emptyTransactionId_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseCustomerTransaction(
              "254708374149", "", new BigDecimal(10.00), "PaymentRequest reversal request", "");
        });
  }

  @Test
  void zeroReversibleAmount_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseCustomerTransaction(
              "254708374149",
              "LGR019G3J2",
              new BigDecimal(0.00),
              "PaymentRequest reversal request",
              "");
        });
  }

  @Test
  void negativeReversibleAmount_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseCustomerTransaction(
              "254708374149",
              "LGR019G3J2",
              new BigDecimal(-10.00),
              "PaymentRequest reversal request",
              "");
        });
  }

  @Test
  void nullReversibleAmount_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseCustomerTransaction(
              "254708374149", "LGR019G3J2", null, "PaymentRequest reversal request", "");
        });
  }

  @Test
  void nullDescription_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseCustomerTransaction(
              "254708374149", "LGR019G3J2", new BigDecimal(10.00), null, "");
        });
  }

  @Test
  void emptyDescription_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.reverseCustomerTransaction(
              "254708374149", "LGR019G3J2", new BigDecimal(10.00), "", "");
        });
  }

  @Test
  void nullOccasion_CustomerTransactionReversalTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    mpesa.reverseCustomerTransaction(
        "254708374149",
        "LGR019G3J2",
        new BigDecimal(10.00),
        "PaymentRequest reversal request",
        null);
  }
}
