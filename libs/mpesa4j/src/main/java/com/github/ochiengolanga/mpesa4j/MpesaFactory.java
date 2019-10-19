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
package com.github.ochiengolanga.mpesa4j;

import com.github.ochiengolanga.mpesa4j.auth.Authorization;
import com.github.ochiengolanga.mpesa4j.auth.AuthorizationFactory;
import com.github.ochiengolanga.mpesa4j.config.Configuration;
import com.github.ochiengolanga.mpesa4j.config.ConfigurationContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MpesaFactory implements java.io.Serializable {
  private static final long serialVersionUID = -563983536986910054L;

  private static final Constructor<Mpesa> MPESA_CONSTRUCTOR;
  static final Authorization DEFAULT_AUTHORIZATION =
      AuthorizationFactory.getInstance(ConfigurationContext.getInstance());
  private static final Mpesa SINGLETON;
  private final Configuration conf;

  static {
    String className = "com.github.ochiengolanga.mpesa4j.MpesaImpl";
    Constructor<Mpesa> constructor;
    Class clazz;
    try {
      clazz = Class.forName(className);
      constructor = clazz.getDeclaredConstructor(Configuration.class, Authorization.class);
    } catch (NoSuchMethodException | ClassNotFoundException e) {
      throw new AssertionError(e);
    }
    MPESA_CONSTRUCTOR = constructor;

    try {
      SINGLETON =
          MPESA_CONSTRUCTOR.newInstance(ConfigurationContext.getInstance(), DEFAULT_AUTHORIZATION);
    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
      throw new AssertionError(e);
    }
  }

  /** Creates a MpesaFactory with the root configuration. */
  public MpesaFactory() {
    this(ConfigurationContext.getInstance());
  }

  /**
   * Creates a MpesaFactory with the given configuration.
   *
   * @param conf the configuration to use
   * @since Mpesa4J 2.1.1
   */
  public MpesaFactory(Configuration conf) {
    if (conf == null) {
      throw new NullPointerException("configuration cannot be null");
    }
    this.conf = conf;
  }

  /**
   * Returns a instance associated with the configuration bound to this factory.
   *
   * @return default singleton instance
   */
  public Mpesa getInstance() {
    return getInstance(AuthorizationFactory.getInstance(conf));
  }

  public Mpesa getInstance(Authorization auth) {
    try {
      return MPESA_CONSTRUCTOR.newInstance(conf, auth);
    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
      throw new AssertionError(e);
    }
  }

  /**
   * Returns default singleton Mpesa instance.
   *
   * @return default singleton Mpesa instance
   * @since Mpesa4J 2.2.4
   */
  public static Mpesa getSingleton() {
    return SINGLETON;
  }
}
