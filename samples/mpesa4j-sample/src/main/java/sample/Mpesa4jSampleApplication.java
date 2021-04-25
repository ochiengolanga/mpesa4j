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
package sample;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.models.responses.SalaryPaymentRequestResponse;
import com.github.ochiengolanga.mpesa4j.models.types.Description;
import com.github.ochiengolanga.mpesa4j.models.types.Occasion;
import com.github.ochiengolanga.mpesa4j.models.types.PhoneNumber;
import com.github.ochiengolanga.mpesa4j.models.types.TransactionAmount;
import java.math.BigDecimal;

/**
 * Sample plain java application using Mpesa4j library to do a Business to Customer (B2C) salary
 * payment transactions between a company and its employees
 *
 * @author Daniel Ochieng' Olanga
 */
public final class Mpesa4jSampleApplication {

  public static void main(String[] args) {
    Mpesa mpesa = new MpesaFactory().getInstance();
    SalaryPaymentRequestResponse response =
        mpesa.paySalary(
            PhoneNumber.of("254708374149"),
            TransactionAmount.of(new BigDecimal("100.00")),
            Description.of("Salary payment (JUL-AUG)"),
            Occasion.of("Test"));

    System.out.println("Response Description: " + response.getResponseDescription());
    System.out.println("Response Code: " + response.getResponseCode());
    System.out.println("ConversationID: " + response.getConversationId());
    System.out.println("OriginatorConversationID: " + response.getOriginatorConversationId());

    System.exit(0);
  }
}
