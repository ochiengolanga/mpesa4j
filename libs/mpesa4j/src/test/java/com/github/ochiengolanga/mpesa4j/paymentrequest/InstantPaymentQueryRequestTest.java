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
package com.github.ochiengolanga.mpesa4j.paymentrequest;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstantPaymentQueryRequestTest {

  @BeforeEach
  void init() {}

  //    @Test
  //    void paymentQueryTest() {
  //      Mpesa mpesa = new MpesaFactory().getInstance();
  //
  //      InstantPaymentRequestResponse paymentRequestResponse =
  //          mpesa.requestInstantPayment(
  //              "254724488116",
  //              new BigDecimal(10.00),
  //              "TESTMPESA4J4Query",
  //              "Instant payment query test from mpesa4j");
  //
  //      assertNotNull(paymentRequestResponse.getCheckoutRequestId());
  //
  //      InstantPaymentQueryResponse response =
  //          mpesa.queryInstantPayment(paymentRequestResponse.getCheckoutRequestId());
  //
  //      assertNotNull(response.getMerchantRequestId());
  //      assertNotNull(response.getCheckoutRequestId());
  //      assertNotNull(response.getResponseDescription());
  //      assertNotNull(response.getResultCode());
  //      assertNotNull(response.getResultDescription());
  //      assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  //    }

  @Test
  void nullPaymentId_paymentQueryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(IllegalArgumentException.class, () -> mpesa.queryInstantPayment(null));
  }

  @Test
  void emptyPaymentId_paymentQueryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(IllegalArgumentException.class, () -> mpesa.queryInstantPayment(""));
  }
}
