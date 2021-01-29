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
package com.github.ochiengolanga.mpesa4j.transaction.query;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.responses.BusinessTransactionQueryResponse;
import com.github.ochiengolanga.mpesa4j.models.types.Description;
import com.github.ochiengolanga.mpesa4j.models.types.Occasion;
import com.github.ochiengolanga.mpesa4j.models.types.TransactionId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessPaymentRequestQueryRequestTest {

  @BeforeEach
  void init() {}

  //   Service randomly becomes unavailable
  @Test
  void businessTransactionQueryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    BusinessTransactionQueryResponse response =
        mpesa.queryBusinessTransaction(
            TransactionId.of("LGR019G3J2"),
            Description.of("PaymentRequest query request"),
            Occasion.none());

    assertNotNull(response.getConversationId());
    assertNotNull(response.getOriginatorConversationId());
    assertNotNull(response.getResponseDescription());
    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  }

  @Test
  void nullTransactionID_businessTransactionQueryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.queryBusinessTransaction(
                null, Description.of("PaymentRequest query request"), Occasion.none()));
  }

  @Test
  void emptyTransactionID_businessTransactionQueryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.queryBusinessTransaction(
                TransactionId.of(""),
                Description.of("PaymentRequest query request"),
                Occasion.none()));
  }

  @Test
  void nullDescription_businessTransactionQueryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.queryBusinessTransaction(TransactionId.of("LGR019G3J2"), null, Occasion.none()));
  }

  @Test
  void emptyDescription_businessTransactionQueryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.queryBusinessTransaction(
                TransactionId.of("LGR019G3J2"), Description.of(""), Occasion.none()));
  }

  // Service randomly becomes unavailable
  //  @Test
  //  void nullOccasion_businessTransactionQueryTest() {
  //    Mpesa mpesa = new MpesaFactory().getInstance();
  //
  //    mpesa.queryBusinessTransaction("LGR019G3J2", "PaymentRequest query request", null);
  //  }
}
