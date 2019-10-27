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
package com.github.ochiengolanga.mpesa4j.spring.boot.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.spring.boot.autoconfigure.properties.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class Mpesa4jAutoConfigurationTest {
  private AnnotationConfigApplicationContext context;

  @BeforeEach
  public void init() {
    this.context = new AnnotationConfigApplicationContext();
    TestPropertyValues.of(
            "mpesa4j.debug=true",
            "mpesa4j.sandbox-enabled=true",
            "mpesa4j.connection-timeout=20000",
            "mpesa4j.read-timeout=120000",
            "mpesa4j.account-balance.queue-timeout-url=https://example.com/accountBalance/queueTimeout",
            "mpesa4j.account-balance.result-url=https://example.com/accountBalance/result",
            "mpesa4j.b2b.queue-timeout-url=https://example.com/b2b/queueTimeout",
            "mpesa4j.b2b.result-url=https://example.com/b2b/result",
            "mpesa4j.b2c.queue-timeout-url=https://example.com/b2c/queueTimeout",
            "mpesa4j.b2c.result-url=https://example.com/b2c/result",
            "mpesa4j.consumer-key=testConsumerKey",
            "mpesa4j.consumer-secret=testConsumerSecret",
            "mpesa4j.initiator.name=testInitiatorName",
            "mpesa4j.initiator.short-code=1234",
            "mpesa4j.initiator.security-credential=testInitiatorSecurityCredential",
            "mpesa4j.lipa-na-mpesa.short-code=testLipaNaMpesaShortCode",
            "mpesa4j.lipa-na-mpesa.passkey=testLipaNaMpesaPasskey",
            "mpesa4j.lipa-na-mpesa.callback-url=https://example.com/lnm/callback",
            "mpesa4j.transaction-query.queue-timeout-url=https://example.com/transactionQuery/queueTimeout",
            "mpesa4j.transaction-query.result-url=https://example.com/transactionQuery/result",
            "mpesa4j.transaction-reversal.queue-timeout-url=https://example.com/transactionReversal/queueTimeout",
            "mpesa4j.transaction-reversal.result-url=https://example.com/transactionReversal/result")
        .applyTo(this.context);
  }

  @AfterEach
  public void closeContext() {
    if (this.context != null) {
      this.context.close();
    }
  }

  @Test
  public void testDefaultConfiguration() {
    this.context.register(
        Mpesa4jAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
    this.context.refresh();

    assertThat(this.context.getBeanNamesForType(MpesaFactory.class)).hasSize(1);
    assertThat(this.context.getBeanNamesForType(Mpesa.class)).hasSize(1);
    assertThat(this.context.getBeanNamesForType(AccountBalanceProperties.class)).hasSize(1);
    assertThat(this.context.getBeanNamesForType(BaseProperties.class)).hasSize(1);
    assertThat(this.context.getBeanNamesForType(B2BProperties.class)).hasSize(1);
    assertThat(this.context.getBeanNamesForType(B2CProperties.class)).hasSize(1);
    assertThat(this.context.getBeanNamesForType(InitiatorProperties.class)).hasSize(1);
    assertThat(this.context.getBeanNamesForType(LipaNaMpesaProperties.class)).hasSize(1);
    assertThat(this.context.getBeanNamesForType(TransactionQueryProperties.class)).hasSize(1);
    assertThat(this.context.getBeanNamesForType(TransactionReversalProperties.class)).hasSize(1);
  }

  @Test
  public void testBasePropertiesDefaults() {
    BaseProperties properties = new BaseProperties();
    assertTrue(!properties.getDebug());
    assertTrue(properties.getSandBoxEnabled());
  }

  @Test
  public void testBaseProperties() {
    BaseProperties properties = new BaseProperties();
    properties.setDebug(true);
    properties.setSandBoxEnabled(false);

    assertTrue(properties.getDebug());
    assertTrue(!properties.getSandBoxEnabled());
  }

  @Test
  public void testAccountBalanceProperties_afterConfiguration() {
    this.context.register(
        Mpesa4jAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
    this.context.refresh();

    AccountBalanceProperties properties = this.context.getBean(AccountBalanceProperties.class);

    assertEquals(
        "https://example.com/accountBalance/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/accountBalance/result", properties.getResultUrl());
  }

  @Test
  public void testB2BProperties_afterConfiguration() {
    this.context.register(
        Mpesa4jAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
    this.context.refresh();

    B2BProperties properties = this.context.getBean(B2BProperties.class);

    assertEquals("https://example.com/b2b/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/b2b/result", properties.getResultUrl());
  }

  @Test
  public void testB2CProperties_afterConfiguration() {
    this.context.register(
        Mpesa4jAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
    this.context.refresh();

    B2CProperties properties = this.context.getBean(B2CProperties.class);

    assertEquals("https://example.com/b2c/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/b2c/result", properties.getResultUrl());
  }

  @Test
  public void testBaseProperties_afterConfiguration() {
    this.context.register(
        Mpesa4jAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
    this.context.refresh();

    BaseProperties properties = this.context.getBean(BaseProperties.class);

    assertTrue(properties.getDebug());
    assertTrue(properties.getSandBoxEnabled());
    assertEquals(20000, properties.getConnectionTimeout());
    assertEquals(120000, properties.getReadTimeout());
    assertEquals("testConsumerKey", properties.getConsumerKey());
    assertEquals("testConsumerSecret", properties.getConsumerSecret());
  }

  @Test
  public void testInitiatorProperties_afterConfiguration() {
    this.context.register(
        Mpesa4jAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
    this.context.refresh();

    InitiatorProperties properties = this.context.getBean(InitiatorProperties.class);

    assertEquals("testInitiatorName", properties.getName());
    assertEquals("1234", properties.getShortCode());
    assertEquals("testInitiatorSecurityCredential", properties.getSecurityCredential());
  }

  @Test
  public void testLipaNaMpesaProperties_afterConfiguration() {
    this.context.register(
        Mpesa4jAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
    this.context.refresh();

    LipaNaMpesaProperties properties = this.context.getBean(LipaNaMpesaProperties.class);

    assertEquals("testLipaNaMpesaShortCode", properties.getShortCode());
    assertEquals("testLipaNaMpesaPasskey", properties.getPasskey());
    assertEquals("https://example.com/lnm/callback", properties.getCallbackUrl());
  }

  @Test
  public void testTransactionQueryProperties_afterConfiguration() {
    this.context.register(
        Mpesa4jAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
    this.context.refresh();

    TransactionQueryProperties properties = this.context.getBean(TransactionQueryProperties.class);

    assertEquals(
        "https://example.com/transactionQuery/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/transactionQuery/result", properties.getResultUrl());
  }

  @Test
  public void testTransactionReversalProperties_afterConfiguration() {
    this.context.register(
        Mpesa4jAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
    this.context.refresh();

    TransactionReversalProperties properties =
        this.context.getBean(TransactionReversalProperties.class);

    assertEquals(
        "https://example.com/transactionReversal/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/transactionReversal/result", properties.getResultUrl());
  }
}
