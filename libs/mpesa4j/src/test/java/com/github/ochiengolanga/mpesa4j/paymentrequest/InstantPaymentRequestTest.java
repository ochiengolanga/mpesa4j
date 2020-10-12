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
package com.github.ochiengolanga.mpesa4j.paymentrequest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.responses.InstantPaymentRequestResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstantPaymentRequestTest {

  @BeforeEach
  void init() {}

  @Test
  void paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    InstantPaymentRequestResponse response =
        mpesa.requestInstantPayment(
            "254724488116",
            new BigDecimal(10.00),
            "TESTMPESA4JRequest",
            "Instant payment request test from mpesa4j");

    assertNotNull(response.getMerchantRequestId());
    assertNotNull(response.getCheckoutRequestId());
    assertNotNull(response.getResponseDescription());
    assertNotNull(response.getCustomerMessage());
    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  }

  @Test
  void exceedMaxPayableAmount_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        MpesaApiException.class,
        () ->
            mpesa.requestInstantPayment(
                "254724488116",
                new BigDecimal(10000000.00),
                "TEST",
                "Invalid instant payment test from mpesa4j"));
  }

  @Test
  void zeroPayableAmount_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.requestInstantPayment(
                "254724488116", new BigDecimal(0.00), "TEST", "Test from mpesa4j"));
  }

  @Test
  void negativePayableAmount_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.requestInstantPayment(
                "254724488116", new BigDecimal(-10.00), "TEST", "Test from mpesa4j"));
  }

  @Test
  void nullPayableAmount_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.requestInstantPayment("254724488116", null, "TEST", "Test from mpesa4j"));
  }

  @Test
  void nullCustomerPhoneNumber_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.requestInstantPayment(null, new BigDecimal(10.00), "TEST", "Test from mpesa4j"));
  }

  @Test
  void emptyCustomerPhoneNumber_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.requestInstantPayment("", new BigDecimal(10.00), "TEST", "Test from mpesa4j"));
  }

  @Test
  void nullAccountReference_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.requestInstantPayment(
                "254724488116", new BigDecimal(10.00), null, "Test from mpesa4j"));
  }

  @Test
  void emptyAccountReference_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.requestInstantPayment(
                "254724488116", new BigDecimal(10.00), "", "Test from mpesa4j"));
  }

  @Test
  void nullDescription_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.requestInstantPayment("254724488116", new BigDecimal(10.00), "TEST", null));
  }

  @Test
  void emptyDescription_paymentRequestTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> mpesa.requestInstantPayment("254724488116", new BigDecimal(10.00), "TEST", ""));
  }
}
