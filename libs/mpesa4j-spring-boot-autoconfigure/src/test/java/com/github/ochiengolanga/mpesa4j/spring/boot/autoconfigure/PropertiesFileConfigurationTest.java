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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;

class PropertiesFileConfigurationTest {

  private AnnotationConfigApplicationContext context;
  private Resource testResource;
  private ResourcePropertySource testPropResource;

  @BeforeEach
  public void setup() throws Exception {
    testResource = new ClassPathResource("application-test.properties");
    testPropResource = new ResourcePropertySource("test", testResource);
  }

  @AfterEach
  public void clear() {
    if (context != null) {
      context.close();
    }
  }

  @Test
  public void testPropertyFileConfiguration() {
    context = initTestContext();

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
  public void testAccountBalanceProperties_afterConfiguration() {
    context = initTestContext();

    AccountBalanceProperties properties = this.context.getBean(AccountBalanceProperties.class);

    assertEquals(
        "https://example.com/accountBalance/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/accountBalance/result", properties.getResultUrl());
  }

  @Test
  public void testB2BProperties_afterConfiguration() {
    context = initTestContext();

    B2BProperties properties = this.context.getBean(B2BProperties.class);

    assertEquals("https://example.com/b2b/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/b2b/result", properties.getResultUrl());
  }

  @Test
  public void testB2CProperties_afterConfiguration() {
    context = initTestContext();

    B2CProperties properties = this.context.getBean(B2CProperties.class);

    assertEquals("https://example.com/b2c/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/b2c/result", properties.getResultUrl());
  }

  @Test
  public void testBaseProperties_afterConfiguration() {
    context = initTestContext();

    BaseProperties properties = this.context.getBean(BaseProperties.class);

    assertTrue(properties.getDebug());
    assertTrue(properties.getSandBoxEnabled());
    assertEquals(20000, properties.getConnectionTimeout());
    assertEquals(120000, properties.getReadTimeout());
    assertEquals("demo", properties.getConsumerKey());
    assertEquals("demo", properties.getConsumerSecret());
  }

  @Test
  public void testInitiatorProperties_afterConfiguration() {
    context = initTestContext();

    InitiatorProperties properties = this.context.getBean(InitiatorProperties.class);

    assertEquals("abcdemo", properties.getName());
    assertEquals("12345", properties.getShortCode());
    assertEquals("Aafs234we", properties.getSecurityCredential());
  }

  @Test
  public void testLipaNaMpesaProperties_afterConfiguration() {
    context = initTestContext();

    LipaNaMpesaProperties properties = this.context.getBean(LipaNaMpesaProperties.class);

    assertEquals("98868", properties.getShortCode());
    assertEquals("wert3434", properties.getPasskey());
    assertEquals("https://example.com/lnm/callback", properties.getCallbackUrl());
  }

  @Test
  public void testTransactionQueryProperties_afterConfiguration() {
    context = initTestContext();

    TransactionQueryProperties properties = this.context.getBean(TransactionQueryProperties.class);

    assertEquals(
        "https://example.com/transactionQuery/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/transactionQuery/result", properties.getResultUrl());
  }

  @Test
  public void testTransactionReversalProperties_afterConfiguration() {
    context = initTestContext();

    TransactionReversalProperties properties =
        this.context.getBean(TransactionReversalProperties.class);

    assertEquals(
        "https://example.com/transactionReversal/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/transactionReversal/result", properties.getResultUrl());
  }

  @Test
  public void testOverridable_AccountBalanceProperties_afterConfiguration() {
    context =
        initTestContext(
            "mpesa4j.account-balance.queue-timeout-url=https://example.com/ab/queueTimeout",
            "mpesa4j.account-balance.result-url=https://example.com/ab/result");

    AccountBalanceProperties properties = this.context.getBean(AccountBalanceProperties.class);

    assertEquals("https://example.com/ab/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/ab/result", properties.getResultUrl());
  }

  @Test
  public void testOverridable_B2BProperties_afterConfiguration() {
    context =
        initTestContext(
            "mpesa4j.b2b.queue-timeout-url=https://example.com/business-2-business/queueTimeout",
            "mpesa4j.b2b.result-url=https://example.com/business-2-business/result");

    B2BProperties properties = this.context.getBean(B2BProperties.class);

    assertEquals(
        "https://example.com/business-2-business/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/business-2-business/result", properties.getResultUrl());
  }

  @Test
  public void testOverridable_B2CProperties_afterConfiguration() {
    context =
        initTestContext(
            "mpesa4j.b2c.queue-timeout-url=https://example.com/business-2-customer/queueTimeout",
            "mpesa4j.b2c.result-url=https://example.com/business-2-customer/result");

    B2CProperties properties = this.context.getBean(B2CProperties.class);

    assertEquals(
        "https://example.com/business-2-customer/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/business-2-customer/result", properties.getResultUrl());
  }

  @Test
  public void testOverridable_BaseProperties_afterConfiguration() {
    context =
        initTestContext(
            "mpesa4j.debug=false",
            "mpesa4j.sandbox-enabled=false",
            "mpesa4j.connection-timeout=40000",
            "mpesa4j.read-timeout=220000",
            "mpesa4j.consumer-key=testConsumerKey",
            "mpesa4j.consumer-secret=testConsumerSecret");

    BaseProperties properties = this.context.getBean(BaseProperties.class);

    assertTrue(!properties.getDebug());
    assertTrue(!properties.getSandBoxEnabled());
    assertEquals(40000, properties.getConnectionTimeout());
    assertEquals(220000, properties.getReadTimeout());
    assertEquals("testConsumerKey", properties.getConsumerKey());
    assertEquals("testConsumerSecret", properties.getConsumerSecret());
  }

  @Test
  public void testOverridable_InitiatorProperties_afterConfiguration() {
    context =
        initTestContext(
            "mpesa4j.initiator.name=testInitiatorName",
            "mpesa4j.initiator.short-code=1234",
            "mpesa4j.initiator.security-credential=testInitiatorSecurityCredential");

    InitiatorProperties properties = this.context.getBean(InitiatorProperties.class);

    assertEquals("testInitiatorName", properties.getName());
    assertEquals("1234", properties.getShortCode());
    assertEquals("testInitiatorSecurityCredential", properties.getSecurityCredential());
  }

  @Test
  public void testOverridable_LipaNaMpesaProperties_afterConfiguration() {
    context =
        initTestContext(
            "mpesa4j.lipa-na-mpesa.short-code=testLipaNaMpesaShortCode",
            "mpesa4j.lipa-na-mpesa.passkey=testLipaNaMpesaPasskey",
            "mpesa4j.lipa-na-mpesa.callback-url=https://example.com/lipanampesa/callback");

    LipaNaMpesaProperties properties = this.context.getBean(LipaNaMpesaProperties.class);

    assertEquals("testLipaNaMpesaShortCode", properties.getShortCode());
    assertEquals("testLipaNaMpesaPasskey", properties.getPasskey());
    assertEquals("https://example.com/lipanampesa/callback", properties.getCallbackUrl());
  }

  @Test
  public void testOverridable_TransactionQueryProperties_afterConfiguration() {
    context =
        initTestContext(
            "mpesa4j.transaction-query.queue-timeout-url=https://example.com/tq/queueTimeout",
            "mpesa4j.transaction-query.result-url=https://example.com/tq/result");

    TransactionQueryProperties properties = this.context.getBean(TransactionQueryProperties.class);

    assertEquals("https://example.com/tq/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/tq/result", properties.getResultUrl());
  }

  @Test
  public void testOverridable_TransactionReversalProperties_afterConfiguration() {
    context =
        initTestContext(
            "mpesa4j.transaction-reversal.queue-timeout-url=https://example.com/tr/queueTimeout",
            "mpesa4j.transaction-reversal.result-url=https://example.com/tr/result");

    TransactionReversalProperties properties =
        this.context.getBean(TransactionReversalProperties.class);

    assertEquals("https://example.com/tr/queueTimeout", properties.getQueueTimeoutUrl());
    assertEquals("https://example.com/tr/result", properties.getResultUrl());
  }

  private AnnotationConfigApplicationContext initTestContext(String... environment) {
    final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    context.getEnvironment().getPropertySources().addLast(testPropResource);
    if (environment.length > 0) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context, environment);
    }

    context.register(Mpesa4jAutoConfiguration.class);
    context.refresh();

    return context;
  }
}
