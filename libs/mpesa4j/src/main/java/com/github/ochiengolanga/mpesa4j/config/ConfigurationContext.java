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

import java.lang.reflect.InvocationTargetException;

/**
 * Static factory of Configuration. This class wraps ConfigurationFactory implementation. <br>
 * By default, PropertyConfigurationFactory will be used.
 */
public final class ConfigurationContext {
  private static final String DEFAULT_CONFIGURATION_FACTORY =
      "com.github.ochiengolanga.mpesa4j.config.PropertyConfigurationFactory";
  private static final String CONFIGURATION_IMPL =
      "com.github.ochiengolanga.mpesa4j.config.configurationFactory";
  private static final ConfigurationFactory factory;

  static {
    String CONFIG_IMPL;
    try {
      CONFIG_IMPL = System.getProperty(CONFIGURATION_IMPL, DEFAULT_CONFIGURATION_FACTORY);
    } catch (SecurityException ignore) {
      CONFIG_IMPL = DEFAULT_CONFIGURATION_FACTORY;
    }

    try {
      factory =
          Class.forName(CONFIG_IMPL)
              .asSubclass(ConfigurationFactory.class)
              .getDeclaredConstructor()
              .newInstance();
    } catch (ClassNotFoundException
        | IllegalAccessException
        | InstantiationException
        | NoSuchMethodException
        | InvocationTargetException cnfe) {
      throw new LinkageError(cnfe.getMessage(), cnfe);
    }
  }

  public static Configuration getInstance() {
    return factory.getInstance();
  }
}
