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
package com.github.ochiengolanga.mpesa4j.payment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.responses.PromotionPaymentRequestResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayPromotionTest {

  @BeforeEach
  void init() {}

  @Test
  void payPromotionTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    PromotionPaymentRequestResponse response =
        mpesa.payPromotion("254708374149", new BigDecimal(100.00), "Promotion payment", "");

    assertNotNull(response.getConversationId());
    assertNotNull(response.getOriginatorConversationId());
    assertNotNull(response.getResponseDescription());
    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  }

  @Test
  void invalidPayableAmount_payPromotionTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        MpesaApiException.class,
        () ->
            mpesa.payPromotion(
                "254708374149", new BigDecimal(10000000.00), "Invalid promotion payment", ""));
  }

  @Test
  void zeroPayableAmount_PayPromotionTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payPromotion("254708374149", new BigDecimal(0.00), "Promotion payment", ""));
  }

  @Test
  void negativePayableAmount_PayPromotionTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payPromotion("254708374149", new BigDecimal(-10.00), "Promotion payment", ""));
  }

  @Test
  void nullPayableAmount_PayPromotionTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payPromotion("254708374149", null, "Promotion payment", ""));
  }

  @Test
  void nullOccasion_payPromotionTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    mpesa.payPromotion("254708374149", new BigDecimal(100.00), "Promotion payment", null);

    assertTrue(true);
  }

  @Test
  void nullDestination_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payPromotion(null, new BigDecimal(100.00), "Promotion payment", null));
  }

  @Test
  void emptyDestination_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payPromotion("", new BigDecimal(100.00), "Promotion payment", null));
  }

  @Test
  void nullDescription_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payPromotion("254708374149", new BigDecimal(100.00), null, ""));
  }

  @Test
  void emptyDescription_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.payPromotion("254708374149", new BigDecimal(100.00), "", ""));
  }
}
