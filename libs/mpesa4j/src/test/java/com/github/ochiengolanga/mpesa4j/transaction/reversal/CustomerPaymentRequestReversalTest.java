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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.responses.CustomerTransactionReversalResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerPaymentRequestReversalTest {

  @BeforeEach
  void init() {}

  @Test
  void customerTransactionReversalTest() {
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
  void nullCustomerPhoneNumber_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseCustomerTransaction(
                null, "LGR019G3J2", new BigDecimal(10.00), "PaymentRequest reversal request", ""));
  }

  @Test
  void emptyCustomerPhoneNumber_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseCustomerTransaction(
                "", "LGR019G3J2", new BigDecimal(10.00), "PaymentRequest reversal request", ""));
  }

  @Test
  void nullTransactionId_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseCustomerTransaction(
                "254708374149",
                null,
                new BigDecimal(10.00),
                "PaymentRequest reversal request",
                ""));
  }

  @Test
  void emptyTransactionId_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseCustomerTransaction(
                "254708374149", "", new BigDecimal(10.00), "PaymentRequest reversal request", ""));
  }

  @Test
  void zeroReversibleAmount_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseCustomerTransaction(
                "254708374149",
                "LGR019G3J2",
                new BigDecimal(0.00),
                "PaymentRequest reversal request",
                ""));
  }

  @Test
  void negativeReversibleAmount_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseCustomerTransaction(
                "254708374149",
                "LGR019G3J2",
                new BigDecimal(-10.00),
                "PaymentRequest reversal request",
                ""));
  }

  @Test
  void nullReversibleAmount_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseCustomerTransaction(
                "254708374149", "LGR019G3J2", null, "PaymentRequest reversal request", ""));
  }

  @Test
  void nullDescription_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseCustomerTransaction(
                "254708374149", "LGR019G3J2", new BigDecimal(10.00), null, ""));
  }

  @Test
  void emptyDescription_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.reverseCustomerTransaction(
                "254708374149", "LGR019G3J2", new BigDecimal(10.00), "", ""));
  }

  @Test
  void nullOccasion_CustomerTransactionReversalTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    mpesa.reverseCustomerTransaction(
        "254708374149",
        "LGR019G3J2",
        new BigDecimal(10.00),
        "PaymentRequest reversal request",
        null);
  }
}
