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

/**
 * ConfigurationFactory implementation for PropertyConfiguration.
 *
 * <p>Currently getInstance calls concrete constructor each time. No caching at all.
 */
class PropertyConfigurationFactory implements ConfigurationFactory {
  private static final PropertyConfiguration ROOT_CONFIGURATION;

  static {
    ROOT_CONFIGURATION = new PropertyConfiguration();
  }

  @Override
  public Configuration getInstance() {
    return ROOT_CONFIGURATION;
  }
}
