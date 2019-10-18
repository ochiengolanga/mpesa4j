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
package com.github.ochiengolanga.mpesa4j.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.ochiengolanga.mpesa4j.config.Configuration;
import com.github.ochiengolanga.mpesa4j.config.ConfigurationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConfigurationTest {
  ConfigurationBuilder builder;
  Configuration conf;

  @BeforeEach
  void beforeEachInit() {
    builder = new ConfigurationBuilder();
  }

  @Test
  void configurationTest() {
    conf = builder.build();

    assertEquals("https://sandbox.safaricom.co.ke", conf.getApiBaseUrl());
    assertEquals("https://sandbox.safaricom.co.ke/mpesa/accountbalance/v1/query", conf.getAccountBalanceApiUrl());
    assertEquals("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest", conf.getBusinessToCustomerPaymentApiUrl());
    assertEquals("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/registerurl", conf.getCallbackUrlRegistrationApiUrl());
    assertEquals("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest", conf.getLipaNaMpesaOnlinePaymentUrl());
    assertEquals("https://sandbox.safaricom.co.ke/mpesa/stkpushquery/v1/query", conf.getLipaNaMpesaOnlineQueryUrl());
    assertEquals("https://sandbox.safaricom.co.ke/oauth/v1/generate", conf.getOAuth2TokenUrl());
    assertEquals("https://sandbox.safaricom.co.ke/mpesa/transactionstatus/v1/query", conf.getTransactionQueryUrl());
    assertEquals("https://sandbox.safaricom.co.ke/mpesa/reversal/v1/request", conf.getTransactionReversalUrl());
  }

  @Test
  void productionConfigurationTest() {
    builder.setSandboxEnabled(false);

    conf = builder.build();

    assertEquals("https://api.safaricom.co.ke", conf.getApiBaseUrl());
    assertEquals("https://api.safaricom.co.ke/mpesa/accountbalance/v1/query", conf.getAccountBalanceApiUrl());
    assertEquals("https://api.safaricom.co.ke/mpesa/b2c/v1/paymentrequest", conf.getBusinessToCustomerPaymentApiUrl());
    assertEquals("https://api.safaricom.co.ke/mpesa/c2b/v1/registerurl", conf.getCallbackUrlRegistrationApiUrl());
    assertEquals("https://api.safaricom.co.ke/mpesa/stkpush/v1/processrequest", conf.getLipaNaMpesaOnlinePaymentUrl());
    assertEquals("https://api.safaricom.co.ke/mpesa/stkpushquery/v1/query", conf.getLipaNaMpesaOnlineQueryUrl());
    assertEquals("https://api.safaricom.co.ke/oauth/v1/generate", conf.getOAuth2TokenUrl());
    assertEquals("https://api.safaricom.co.ke/mpesa/transactionstatus/v1/query", conf.getTransactionQueryUrl());
    assertEquals("https://api.safaricom.co.ke/mpesa/reversal/v1/request", conf.getTransactionReversalUrl());
  }

  @Test
  void configurationBuilderTest() {
    conf = builder.build();

    assertEquals(0, conf.getApiBaseUrl().indexOf("https://"));

    builder = new ConfigurationBuilder();
    builder.setDebugEnabled(true);
    builder.setSandboxEnabled(true);
    builder.setHttpConnectionTimeout(1000);
    builder.setHttpReadTimeout(1000);
    builder.setAccountBalanceQueueTimeoutUrl("https://example.com/accountbalance/queuetimeout");
    builder.setAccountBalanceResultUrl("https://example.com/accountbalance/result");
    //builder.setB2BQueueTimeoutUrl("https://example.com/accountbalance/queuetimeout");
    //builder.setB2BResultUrl("https://example.com/b2b/result");
    builder.setB2CQueueTimeoutUrl("https://example.com/b2c/queuetimeout");
    builder.setB2CResultUrl("https://example.com/b2c/result");
    builder.setConsumerKey("key");
    builder.setConsumerSecret("secret");
    builder.setInitiatorName("abcdemo");
    builder.setInitiatorShortCode("12399");
    builder.setInitiatorSecurityCredential("EMFNv1b/hHBzo9uIWv");
    builder.setLipaNaMpesaShortCode("97867");
    builder.setLipaNaMpesaPasskey("7dd71a467cd2");
    builder.setLipaNaMpesaCallbackUrl("https://example.com/lipanampesa/callback");
    builder.setTransactionQueryQueueTimeoutUrl("https://example.com/transactionquery/queuetimeout");
    builder.setTransactionQueryResultUrl("https://example.com/transactionquery/result");
    builder.setTransactionReversalQueueTimeoutUrl("https://example.com/transactionreversal/queuetimeout");
    builder.setTransactionReversalResultUrl("https://example.com/transactionreversal/result");
    conf = builder.build();

    assertTrue(conf.isDebugEnabled());
    assertTrue(conf.isSandBoxModeEnabled());
    assertEquals(1000, conf.getHttpClientConfiguration().getHttpConnectionTimeout());
    assertEquals(1000, conf.getHttpClientConfiguration().getHttpReadTimeout());
    assertEquals("https://example.com/accountbalance/queuetimeout", conf.getAccountBalanceQueueTimeoutUrl());
    assertEquals("https://example.com/accountbalance/result", conf.getAccountBalanceResultUrl());
    assertEquals("https://example.com/b2c/queuetimeout", conf.getPayBusinessQueueTimeoutUrl());
    assertEquals("https://example.com/b2c/result", conf.getPayBusinessResultUrl());
    assertEquals("https://example.com/b2c/queuetimeout", conf.getPayPromotionQueueTimeoutUrl());
    assertEquals("https://example.com/b2c/result", conf.getPayPromotionResultUrl());
    assertEquals("https://example.com/b2c/queuetimeout", conf.getPaySalaryQueueTimeoutUrl());
    assertEquals("https://example.com/b2c/result", conf.getPaySalaryResultUrl());
    assertEquals("key", conf.getConsumerKey());
    assertEquals("secret", conf.getConsumerSecret());
    assertEquals("abcdemo", conf.getInitiatorName());
    assertEquals("12399", conf.getInitiatorShortCode());
    assertEquals("EMFNv1b/hHBzo9uIWv", conf.getInitiatorSecurityCredential());
    assertEquals("97867", conf.getLipaNaMpesaShortCode());
    assertEquals("7dd71a467cd2", conf.getLipaNaMpesaPasskey());
    assertEquals("https://example.com/lipanampesa/callback", conf.getLipaNaMpesaCallbackUrl());
    assertEquals("https://example.com/transactionquery/queuetimeout", conf.getTransactionQueryQueueTimeoutUrl());
    assertEquals("https://example.com/transactionquery/result", conf.getTransactionQueryResultUrl());
    assertEquals("https://example.com/transactionreversal/queuetimeout", conf.getTransactionReversalQueueTimeoutUrl());
    assertEquals("https://example.com/transactionreversal/result", conf.getTransactionReversalResultUrl());
  }
}
