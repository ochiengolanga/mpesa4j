/*
 * Copyright 2019-2021 Daniel Ochieng' Olanga.
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
import com.github.ochiengolanga.mpesa4j.models.responses.SalaryPaymentRequestResponse;
import com.github.ochiengolanga.mpesa4j.models.types.Description;
import com.github.ochiengolanga.mpesa4j.models.types.Occasion;
import com.github.ochiengolanga.mpesa4j.models.types.PhoneNumber;
import com.github.ochiengolanga.mpesa4j.models.types.TransactionAmount;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PaySalaryTest {

  @BeforeEach
  void init() {}

  @Disabled("Daraja API timeouts randomly or is unavailable")
  @Test
  void paySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    SalaryPaymentRequestResponse response =
        mpesa.paySalary(
            PhoneNumber.of("254708374149"),
            TransactionAmount.of(new BigDecimal("100.00")),
            Description.of("Salary payment"),
            Occasion.none());

    assertNotNull(response.getConversationId());
    assertNotNull(response.getOriginatorConversationId());
    assertNotNull(response.getResponseDescription());
    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
  }

  @Test
  void invalidPayableAmount_paySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        MpesaApiException.class,
        () ->
            mpesa.paySalary(
                PhoneNumber.of("254708374149"),
                TransactionAmount.of(new BigDecimal("10000000.00")),
                Description.of("Invalid salary payment"),
                Occasion.none()));
  }

  @Test
  void zeroPayableAmount_PaySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.paySalary(
                PhoneNumber.of("254708374149"),
                TransactionAmount.of(new BigDecimal("0.00")),
                Description.of("Salary payment"),
                Occasion.none()));
  }

  @Test
  void negativePayableAmount_PaySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.paySalary(
                PhoneNumber.of("254708374149"),
                TransactionAmount.of(new BigDecimal("-10.00")),
                Description.of("Salary payment"),
                Occasion.none()));
  }

  @Test
  void nullPayableAmount_PaySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            mpesa.paySalary(
                PhoneNumber.of("254708374149"),
                null,
                Description.of("Salary payment"),
                Occasion.none()));
  }

  @Test
  void nullOccasion_paySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        MpesaApiException.class,
        () ->
            mpesa.paySalary(
                PhoneNumber.of("254708374149"),
                TransactionAmount.of(new BigDecimal("100.00")),
                Description.of("Salary payment"),
                null));
  }

  @Test
  void nullDestination_paySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            mpesa.paySalary(
                null,
                TransactionAmount.of(new BigDecimal("100.00")),
                Description.of("Salary payment"),
                null));
  }

  @Test
  void emptyDestination_paySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.paySalary(
                PhoneNumber.of(""),
                TransactionAmount.of(new BigDecimal("100.00")),
                Description.of("Salary payment"),
                null));
  }

  @Test
  void nullDescription_paySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        NullPointerException.class,
        () ->
            mpesa.paySalary(
                PhoneNumber.of("254708374149"),
                TransactionAmount.of(new BigDecimal("100.00")),
                null,
                Occasion.none()));
  }

  @Test
  void emptyDescription_paySalaryTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            mpesa.paySalary(
                PhoneNumber.of("254708374149"),
                TransactionAmount.of(new BigDecimal("100.00")),
                Description.of(""),
                Occasion.none()));
  }
}
