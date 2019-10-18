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

import java.math.BigDecimal;
import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.exceptions.MpesaApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InstantPaymentRequestTest {

  @BeforeEach
  void init() {}

  //  @Test
  //  void paymentRequestTest() throws MpesaApiException {
  //    Mpesa mpesa = new MpesaFactory().getInstance();
  //
  //    InstantPaymentRequestResponse response =
  //        mpesa.requestInstantPayment(
  //            "254724488116", new BigDecimal(10.00), "TESTMPESA4J2", "Test from mpesa4j");
  //
  //    assertNotNull(response.getMerchantRequestId());
  //    assertNotNull(response.getCheckoutRequestId());
  //    assertNotNull(response.getResponseDescription());
  //    assertNotNull(response.getCustomerMessage());
  //    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  //  }

  @Test
  void zeroPayableAmount_paymentRequestTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.requestInstantPayment(
              "254724488116", new BigDecimal(0.00), "TEST", "Test from mpesa4j");
        });
  }

  @Test
  void negativePayableAmount_paymentRequestTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.requestInstantPayment(
              "254724488116", new BigDecimal(-10.00), "TEST", "Test from mpesa4j");
        });
  }

  @Test
  void nullPayableAmount_paymentRequestTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.requestInstantPayment("254724488116", null, "TEST", "Test from mpesa4j");
        });
  }

  @Test
  void nullCustomerPhoneNumber_paymentRequestTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.requestInstantPayment(null, new BigDecimal(10.00), "TEST", "Test from mpesa4j");
        });
  }

  @Test
  void emptyCustomerPhoneNumber_paymentRequestTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.requestInstantPayment("", new BigDecimal(10.00), "TEST", "Test from mpesa4j");
        });
  }

  @Test
  void nullAccountReference_paymentRequestTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.requestInstantPayment(
              "254724488116", new BigDecimal(10.00), null, "Test from mpesa4j");
        });
  }

  @Test
  void emptyAccountReference_paymentRequestTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.requestInstantPayment(
              "254724488116", new BigDecimal(10.00), "", "Test from mpesa4j");
        });
  }

  @Test
  void nullDescription_paymentRequestTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.requestInstantPayment("254724488116", new BigDecimal(10.00), "TEST", null);
        });
  }

  @Test
  void emptyDescription_paymentRequestTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.requestInstantPayment("254724488116", new BigDecimal(10.00), "TEST", "");
        });
  }
}
