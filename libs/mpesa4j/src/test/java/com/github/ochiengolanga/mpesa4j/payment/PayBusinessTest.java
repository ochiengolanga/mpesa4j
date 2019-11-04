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
package com.github.ochiengolanga.mpesa4j.payment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.responses.BusinessPaymentRequestResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayBusinessTest {

  @BeforeEach
  void init() {}

  @Test
  void payBusinessTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    BusinessPaymentRequestResponse response =
        mpesa.payBusiness("254708374149", new BigDecimal(100.00), "Business payment", "");

    assertNotNull(response.getConversationId());
    assertNotNull(response.getOriginatorConversationId());
    assertNotNull(response.getResponseDescription());
    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  }

  @Test
  void invalidPayableAmount_payBusinessTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        MpesaApiException.class,
        () ->
            mpesa.payBusiness(
                "254708374149", new BigDecimal(10000000.00), "Invalid business payment", ""));
  }

  @Test
  void zeroPayableAmount_PayBusinessTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payBusiness("254708374149", new BigDecimal(0.00), "Business payment", ""));
  }

  @Test
  void negativePayableAmount_PayBusinessTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payBusiness("254708374149", new BigDecimal(-10.00), "Business payment", ""));
  }

  @Test
  void nullPayableAmount_PayBusinessTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payBusiness("254708374149", null, "Business payment", ""));
  }

  @Test
  void nullOccasion_payBusinessTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    mpesa.payBusiness("254708374149", new BigDecimal(100.00), "Business payment", null);

    assertTrue(true);
  }

  @Test
  void nullDestination_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payBusiness(null, new BigDecimal(100.00), "Business payment", null));
  }

  @Test
  void emptyDestination_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payBusiness("", new BigDecimal(100.00), "Business payment", null));
  }

  @Test
  void nullDescription_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payBusiness("254708374149", new BigDecimal(100.00), null, ""));
  }

  @Test
  void emptyDescription_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payBusiness("254708374149", new BigDecimal(100.00), "", ""));
  }
}
