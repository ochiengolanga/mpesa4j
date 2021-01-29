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
package com.github.ochiengolanga.mpesa4j.config;

/**
 * A builder that can be used to construct a mpesa4j configuration with desired settings. This
 * builder has sensible defaults such that {@code new ConfigurationBuilder().build()} would create a
 * usable configuration. This configuration builder is useful for clients that wish to configure
 * mpesa4j in unit tests or from command line flags for example.
 */
public final class ConfigurationBuilder {
  private ConfigurationBase configurationBean = new PropertyConfiguration();

  public ConfigurationBuilder setDebugEnabled(boolean debugEnabled) {
    checkNotBuilt();
    configurationBean.setDebug(debugEnabled);
    return this;
  }

  public ConfigurationBuilder setSandboxEnabled(boolean sandboxEnabled) {
    checkNotBuilt();
    configurationBean.setSandBoxModeEnabled(sandboxEnabled);
    return this;
  }

  public ConfigurationBuilder setHttpConnectionTimeout(int timeout) {
    checkNotBuilt();
    configurationBean.setHttpConnectionTimeout(timeout);
    return this;
  }

  public ConfigurationBuilder setHttpReadTimeout(int timeout) {
    checkNotBuilt();
    configurationBean.setHttpReadTimeout(timeout);
    return this;
  }

  public ConfigurationBuilder setAccountBalanceQueueTimeoutUrl(String url) {
    checkNotBuilt();
    configurationBean.setAccountBalanceQueueTimeoutUrl(url);
    return this;
  }

  public ConfigurationBuilder setAccountBalanceResultUrl(String url) {
    checkNotBuilt();
    configurationBean.setAccountBalanceResultUrl(url);
    return this;
  }

  public ConfigurationBuilder setB2BQueueTimeoutUrl(String url) {
    checkNotBuilt();
    return this;
  }

  public ConfigurationBuilder setB2BResultUrl(String url) {
    checkNotBuilt();
    return this;
  }

  public ConfigurationBuilder setB2CQueueTimeoutUrl(String url) {
    checkNotBuilt();
    configurationBean.setPayBusinessQueueTimeoutUrl(url);
    configurationBean.setPayPromotionQueueTimeoutUrl(url);
    configurationBean.setPaySalaryQueueTimeoutUrl(url);
    return this;
  }

  public ConfigurationBuilder setB2CResultUrl(String url) {
    checkNotBuilt();
    configurationBean.setPayBusinessResultUrl(url);
    configurationBean.setPayPromotionResultUrl(url);
    configurationBean.setPaySalaryResultUrl(url);
    return this;
  }

  public ConfigurationBuilder setConsumerKey(String consumerKey) {
    checkNotBuilt();
    configurationBean.setConsumerKey(consumerKey);
    return this;
  }

  public ConfigurationBuilder setConsumerSecret(String consumerSecret) {
    checkNotBuilt();
    configurationBean.setConsumerSecret(consumerSecret);
    return this;
  }

  public ConfigurationBuilder setInitiatorName(String initiatorName) {
    checkNotBuilt();
    configurationBean.setInitiatorName(initiatorName);
    return this;
  }

  public ConfigurationBuilder setInitiatorShortCode(String initiatorShortCode) {
    checkNotBuilt();
    configurationBean.setInitiatorShortCode(initiatorShortCode);
    return this;
  }

  public ConfigurationBuilder setInitiatorSecurityCredential(String securityCredential) {
    checkNotBuilt();
    configurationBean.setInitiatorSecurityCredential(securityCredential);
    return this;
  }

  public ConfigurationBuilder setLipaNaMpesaShortCode(String shortCode) {
    checkNotBuilt();
    configurationBean.setLipaNaMpesaShortCode(shortCode);
    return this;
  }

  public ConfigurationBuilder setLipaNaMpesaPasskey(String passkey) {
    checkNotBuilt();
    configurationBean.setLipaNaMpesaPasskey(passkey);
    return this;
  }

  public ConfigurationBuilder setLipaNaMpesaCallbackUrl(String url) {
    checkNotBuilt();
    configurationBean.setLipaNaMpesaCallbackUrl(url);
    return this;
  }

  public ConfigurationBuilder setTransactionQueryQueueTimeoutUrl(String url) {
    checkNotBuilt();
    configurationBean.setTransactionQueryQueueTimeoutUrl(url);
    return this;
  }

  public ConfigurationBuilder setTransactionQueryResultUrl(String url) {
    checkNotBuilt();
    configurationBean.setTransactionQueryResultUrl(url);
    return this;
  }

  public ConfigurationBuilder setTransactionReversalQueueTimeoutUrl(String url) {
    checkNotBuilt();
    configurationBean.setTransactionReversalQueueTimeoutUrl(url);
    return this;
  }

  public ConfigurationBuilder setTransactionReversalResultUrl(String url) {
    checkNotBuilt();
    configurationBean.setTransactionReversalResultUrl(url);
    return this;
  }

  public Configuration build() {
    checkNotBuilt();
    try {
      return configurationBean;
    } finally {
      configurationBean = null;
    }
  }

  private void checkNotBuilt() {
    if (configurationBean == null) {
      throw new IllegalStateException(
          "Cannot use this builder any longer, build() has already been called");
    }
  }
}
