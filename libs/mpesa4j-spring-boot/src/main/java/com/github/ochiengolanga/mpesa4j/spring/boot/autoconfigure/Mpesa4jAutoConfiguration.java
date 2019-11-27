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

import com.github.ochiengolanga.mpesa4j.Mpesa;
import com.github.ochiengolanga.mpesa4j.MpesaFactory;
import com.github.ochiengolanga.mpesa4j.config.ConfigurationBuilder;
import com.github.ochiengolanga.mpesa4j.spring.boot.autoconfigure.properties.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConditionalOnClass({MpesaFactory.class})
@EnableConfigurationProperties({
  BaseProperties.class,
  AccountBalanceProperties.class,
  B2BProperties.class,
  B2CProperties.class,
  InitiatorProperties.class,
  LipaNaMpesaProperties.class,
  TransactionQueryProperties.class,
  TransactionReversalProperties.class
})
public class Mpesa4jAutoConfiguration {

  private static Log log = LogFactory.getLog(Mpesa4jAutoConfiguration.class);

  private final BaseProperties baseProperties;
  private final AccountBalanceProperties accountBalanceProperties;
  private final B2BProperties b2bProperties;
  private final B2CProperties b2cProperties;
  private final InitiatorProperties initiatorProperties;
  private final LipaNaMpesaProperties lipaNaMpesaProperties;
  private final TransactionQueryProperties transactionQueryProperties;
  private final TransactionReversalProperties transactionReversalProperties;

  @Bean
  @ConditionalOnMissingBean
  public MpesaFactory mpesaFactory() {
    if (this.baseProperties.getConsumerKey() == null
        || this.baseProperties.getConsumerSecret() == null) {
      String msg =
          "Mpesa4j baseProperties not configured properly."
              + " Please check mpesa4j.* baseProperties settings in configuration file.";
      log.error(msg);
      throw new RuntimeException(msg);
    }

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(baseProperties.getDebug())
        .setSandboxEnabled(baseProperties.getSandBoxEnabled())
        .setHttpConnectionTimeout(baseProperties.getConnectionTimeout())
        .setHttpReadTimeout(baseProperties.getReadTimeout())
        .setAccountBalanceQueueTimeoutUrl(accountBalanceProperties.getQueueTimeoutUrl())
        .setAccountBalanceResultUrl(accountBalanceProperties.getQueueTimeoutUrl())
        .setB2BQueueTimeoutUrl(b2bProperties.getQueueTimeoutUrl())
        .setB2BResultUrl(b2bProperties.getResultUrl())
        .setB2CQueueTimeoutUrl(b2cProperties.getQueueTimeoutUrl())
        .setB2CResultUrl(b2cProperties.getResultUrl())
        .setConsumerKey(baseProperties.getConsumerKey())
        .setConsumerSecret(baseProperties.getConsumerSecret())
        .setInitiatorName(initiatorProperties.getName())
        .setInitiatorShortCode(initiatorProperties.getShortCode())
        .setInitiatorSecurityCredential(initiatorProperties.getSecurityCredential())
        .setLipaNaMpesaShortCode(lipaNaMpesaProperties.getShortCode())
        .setLipaNaMpesaPasskey(lipaNaMpesaProperties.getPasskey())
        .setLipaNaMpesaCallbackUrl(lipaNaMpesaProperties.getCallbackUrl())
        .setTransactionQueryQueueTimeoutUrl(transactionQueryProperties.getQueueTimeoutUrl())
        .setTransactionQueryResultUrl(transactionQueryProperties.getResultUrl())
        .setTransactionReversalQueueTimeoutUrl(transactionReversalProperties.getQueueTimeoutUrl())
        .setTransactionReversalResultUrl(transactionReversalProperties.getResultUrl());
    return new MpesaFactory(cb.build());
  }

  @Bean
  @ConditionalOnMissingBean
  public Mpesa mpesa(MpesaFactory mpesaFactory) {
    return mpesaFactory.getInstance();
  }
}
