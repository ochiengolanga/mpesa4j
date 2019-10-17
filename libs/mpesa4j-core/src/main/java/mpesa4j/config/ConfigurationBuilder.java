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
package mpesa4j.config;

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

  public ConfigurationBuilder setLipaNaMpesaKey(String passkey) {
    checkNotBuilt();
    configurationBean.setLipaNaMpesaPasskey(passkey);
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
