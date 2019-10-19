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
package com.github.ochiengolanga.mpesa4j.transaction.query;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.exceptions.MpesaApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerPaymentRequestQueryRequestTest {

  @BeforeEach
  void init() {}

  //  @Test
  //  void customerTransactionQueryTest() throws MpesaApiException {
  //    Mpesa mpesa = new MpesaFactory().getInstance();
  //    // mpesa.setOAuth2Token(new OAuth2Token("r9ufPKRyeyg2TGGVZehVD6ADR10W"));
  //
  //    CustomerTransactionQueryResponse response =
  //        mpesa.queryCustomerTransaction(
  //            "254708374149", "LGR019G3J2", "PaymentRequest query request", "");
  //
  //    assertNotNull(response.getConversationId());
  //    assertNotNull(response.getOriginatorConversationId());
  //    assertNotNull(response.getResponseDescription());
  //    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  //  }

  @Test
  void nullCustomerPhoneNumber_customerTransactionQueryTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();
    // mpesa.setOAuth2Token(new OAuth2Token("r9ufPKRyeyg2TGGVZehVD6ADR10W"));

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.queryCustomerTransaction(null, "LGR019G3J2", "PaymentRequest query request", "");
        });
  }

  @Test
  void emptyCustomerPhoneNumber_customerTransactionQueryTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();
    // mpesa.setOAuth2Token(new OAuth2Token("r9ufPKRyeyg2TGGVZehVD6ADR10W"));

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.queryCustomerTransaction("", "LGR019G3J2", "PaymentRequest query request", "");
        });
  }

  @Test
  void nullTransactionID_customerTransactionQueryTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();
    // mpesa.setOAuth2Token(new OAuth2Token("r9ufPKRyeyg2TGGVZehVD6ADR10W"));

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.queryCustomerTransaction("254708374149", null, "PaymentRequest query request", "");
        });
  }

  @Test
  void emptyTransactionID_customerTransactionQueryTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();
    // mpesa.setOAuth2Token(new OAuth2Token("r9ufPKRyeyg2TGGVZehVD6ADR10W"));

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.queryCustomerTransaction("254708374149", "", "PaymentRequest query request", "");
        });
  }

  @Test
  void nullDescription_customerTransactionQueryTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();
    // mpesa.setOAuth2Token(new OAuth2Token("r9ufPKRyeyg2TGGVZehVD6ADR10W"));

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.queryCustomerTransaction("254708374149", "LGR019G3J2", null, "");
        });
  }

  @Test
  void emptyDescription_customerTransactionQueryTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();
    // mpesa.setOAuth2Token(new OAuth2Token("r9ufPKRyeyg2TGGVZehVD6ADR10W"));

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          mpesa.queryCustomerTransaction("254708374149", "LGR019G3J2", "", "");
        });
  }

  @Test
  void nullOccasion_customerTransactionQueryTest() throws MpesaApiException {
    Mpesa mpesa = new MpesaFactory().getInstance();
    // mpesa.setOAuth2Token(new OAuth2Token("r9ufPKRyeyg2TGGVZehVD6ADR10W"));

    mpesa.queryCustomerTransaction(
        "254708374149", "LGR019G3J2", "PaymentRequest query request", null);
  }
}
