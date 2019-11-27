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
package com.github.ochiengolanga.mpesa4j.accountbalance;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountBalanceRequestTest {

  @BeforeEach
  void init() {}

  // 27th November 2019 API error  Activity timed out Job-2097071 Error in [GenericWrapper/Business Processes/SubProcesses/AccountBalance.process/BrokerProxy]
//  @Test
//  void accountBalanceTest() {
//    Mpesa mpesa = new MpesaFactory().getInstance();
//
//    AccountBalanceResponse response = mpesa.queryBalance("Account balance request");
//
//    assertNotNull(response.getConversationId());
//    assertNotNull(response.getOriginatorConversationId());
//    assertNotNull(response.getResponseDescription());
//    assertTrue(response.getResponseCode().equalsIgnoreCase("0"));
//  }

  @Test
  void nullDescription_accountBalanceTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(IllegalArgumentException.class, () -> mpesa.queryBalance(null));
  }

  @Test
  void emptyDescription_accountBalanceTest() {
    Mpesa mpesa = new MpesaFactory().getInstance();

    Assertions.assertThrows(IllegalArgumentException.class, () -> mpesa.queryBalance(""));
  }
}
