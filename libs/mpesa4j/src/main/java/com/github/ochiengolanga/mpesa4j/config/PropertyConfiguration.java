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

import com.google.common.base.Splitter;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public final class PropertyConfiguration extends ConfigurationBase implements java.io.Serializable {
  private static final long serialVersionUID = -7262615247923693252L;

  private static final String DEBUG = "MPESA4J_DEBUG";
  private static final String SANDBOX_ENABLED = "MPESA4J_SANDBOX_ENABLED";
  private static final String HTTP_CONNECTION_TIMEOUT = "MPESA4J_HTTP_CONNECTION_TIMEOUT";
  private static final String HTTP_READ_TIMEOUT = "MPESA4J_HTTP_READ_TIMEOUT";
  private static final String ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL =
      "MPESA4J_ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL";
  private static final String ACCOUNT_BALANCE_RESULT_URL = "MPESA4J_ACCOUNT_BALANCE_RESULT_URL";
  private static final String B2B_QUEUE_TIMEOUT_URL = "MPESA4J_B2B_QUEUE_TIMEOUT_URL";
  private static final String B2B_RESULT_URL = "MPESA4J_B2B_BALANCE_RESULT_URL";
  private static final String B2C_QUEUE_TIMEOUT_URL = "MPESA4J_B2C_QUEUE_TIMEOUT_URL";
  private static final String B2C_RESULT_URL = "MPESA4J_B2C_BALANCE_RESULT_URL";
  private static final String CONSUMER_KEY = "MPESA4J_CONSUMER_KEY";
  private static final String CONSUMER_SECRET = "MPESA4J_CONSUMER_SECRET";
  private static final String INITIATOR_NAME = "MPESA4J_INITIATOR_NAME";
  private static final String INITIATOR_SHORTCODE = "MPESA4J_INITIATOR_SHORTCODE";
  private static final String INITIATOR_SECURITY_CREDENTIAL =
      "MPESA4J_INITIATOR_SECURITY_CREDENTIAL";
  private static final String LIPA_NA_MPESA_SHORTCODE = "MPESA4J_LIPA_NA_MPESA_SHORTCODE";
  private static final String LIPA_NA_MPESA_PASSKEY = "MPESA4J_LIPA_NA_MPESA_PASSKEY";
  private static final String LIPA_NA_MPESA_CALLBACK_URL = "MPESA4J_LIPA_NA_MPESA_CALLBACK_URL";
  private static final String TRANSACTION_QUERY_QUEUE_TIMEOUT_URL =
      "MPESA4J_TRANSACTION_QUERY_QUEUE_TIMEOUT_URL";
  private static final String TRANSACTION_QUERY_RESULT_URL = "MPESA4J_TRANSACTION_QUERY_RESULT_URL";
  private static final String TRANSACTION_REVERSAL_QUEUE_TIMEOUT_URL =
      "MPESA4J_TRANSACTION_REVERSAL_QUEUE_TIMEOUT_URL";
  private static final String TRANSACTION_REVERSAL_RESULT_URL =
      "MPESA4J_TRANSACTION_REVERSAL_RESULT_URL";

  PropertyConfiguration(String treePath) {
    super();
    Properties props;

    // load from system properties
    try {
      props = (Properties) System.getProperties().clone();
      try {
        Map<String, String> envMap = System.getenv();
        for (String key : envMap.keySet()) {
          props.setProperty(key, envMap.get(key));
        }
      } catch (SecurityException ignore) {
      }
      normalize(props);
    } catch (SecurityException ignore) {
      // Unsigned applets are not allowed to access System properties
      props = new Properties();
    }

    final String MPESA4J_PROPERTIES = "mpesa4j.properties";
    // override System properties with ./mpesa4j.properties in the classpath
    loadProperties(props, "." + File.separatorChar + MPESA4J_PROPERTIES);

    // then, override with /mpesa4j.properties in the classpath
    loadProperties(props, Configuration.class.getResourceAsStream("/" + MPESA4J_PROPERTIES));

    // then, override with /WEB/INF/mpesa4j.properties in the classpath
    loadProperties(
        props, Configuration.class.getResourceAsStream("/WEB-INF/" + MPESA4J_PROPERTIES));

    try {
      loadProperties(props, new FileInputStream("WEB-INF/" + MPESA4J_PROPERTIES));
    } catch (SecurityException | FileNotFoundException ignore) {
    }

    setFieldsWithTreePath(props, treePath);
  }

  /**
   * Creates a root PropertyConfiguration. This constructor is equivalent to new
   * PropertyConfiguration("/").
   */
  PropertyConfiguration() {
    this("/");
  }

  private boolean notNull(Properties props, String prefix, String name) {
    return props.getProperty(prefix + name) != null;
  }

  private boolean loadProperties(Properties props, String path) {
    FileInputStream fis = null;
    try {
      File file = new File(path);
      if (file.exists() && file.isFile()) {
        fis = new FileInputStream(file);
        props.load(fis);
        normalize(props);
        return true;
      }
    } catch (Exception ignore) {
    } finally {
      try {
        if (fis != null) {
          fis.close();
        }
      } catch (IOException ignore) {

      }
    }
    return false;
  }

  private boolean loadProperties(Properties props, InputStream is) {
    try {
      props.load(is);
      normalize(props);
      return true;
    } catch (Exception ignore) {
    }
    return false;
  }

  private void normalize(Properties props) {
    ArrayList<String> toBeNormalized = new ArrayList<String>(10);
    for (Object key : props.keySet()) {
      String keyStr = (String) key;
      if (keyStr.contains("MPESA4J_")) {
        toBeNormalized.add(keyStr);
      }
    }
    for (String keyStr : toBeNormalized) {
      String property = props.getProperty(keyStr);
      int index = keyStr.indexOf("MPESA4J_");
      String newKey = keyStr.substring(0, index) + keyStr.substring(index + 8);
      props.setProperty(newKey, property);
    }
  }

  /**
   * passing "/foo/bar" as treePath will result:<br>
   * 1. load [mpesa4j.]restBaseURL<br>
   * 2. override the value with foo.[mpesa4j.]restBaseURL<br>
   * 3. override the value with foo.bar.[mpesa4j.]restBaseURL<br>
   *
   * @param props properties to be loaded
   * @param treePath the path
   */
  private void setFieldsWithTreePath(Properties props, String treePath) {
    setFieldsWithPrefix(props, "");
    Iterable<String> splitArray = Splitter.on('/').split(treePath);
    String prefix = null;
    for (String split : splitArray) {
      if (!"".equals(split)) {
        if (null == prefix) {
          prefix = split + ".";
        } else {
          prefix += split + ".";
        }
        setFieldsWithPrefix(props, prefix);
      }
    }
  }

  private void setFieldsWithPrefix(Properties props, String prefix) {
    if (notNull(props, prefix, DEBUG)) {
      setDebug(getBoolean(props, prefix, DEBUG));
    }

    if (notNull(props, prefix, SANDBOX_ENABLED)) {
      setSandBoxModeEnabled(getBoolean(props, prefix, SANDBOX_ENABLED));
    }

    if (notNull(props, prefix, HTTP_CONNECTION_TIMEOUT)) {
      setHttpConnectionTimeout(getIntProperty(props, prefix, HTTP_CONNECTION_TIMEOUT));
    }
    if (notNull(props, prefix, HTTP_READ_TIMEOUT)) {
      setHttpReadTimeout(getIntProperty(props, prefix, HTTP_READ_TIMEOUT));
    }

    if (notNull(props, prefix, ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL)) {
      setAccountBalanceQueueTimeoutUrl(getString(props, prefix, ACCOUNT_BALANCE_QUEUE_TIMEOUT_URL));
    }
    if (notNull(props, prefix, ACCOUNT_BALANCE_RESULT_URL)) {
      setAccountBalanceResultUrl(getString(props, prefix, ACCOUNT_BALANCE_RESULT_URL));
    }

    if (notNull(props, prefix, B2B_QUEUE_TIMEOUT_URL)) {}

    if (notNull(props, prefix, B2B_RESULT_URL)) {}

    if (notNull(props, prefix, B2C_QUEUE_TIMEOUT_URL)) {
      setPayBusinessQueueTimeoutUrl(getString(props, prefix, B2C_QUEUE_TIMEOUT_URL));
      setPayPromotionQueueTimeoutUrl(getString(props, prefix, B2C_QUEUE_TIMEOUT_URL));
      setPaySalaryQueueTimeoutUrl(getString(props, prefix, B2C_QUEUE_TIMEOUT_URL));
    }

    if (notNull(props, prefix, B2C_RESULT_URL)) {
      setPayBusinessResultUrl(getString(props, prefix, B2C_RESULT_URL));
      setPayPromotionResultUrl(getString(props, prefix, B2C_RESULT_URL));
      setPaySalaryResultUrl(getString(props, prefix, B2C_RESULT_URL));
    }

    if (notNull(props, prefix, CONSUMER_KEY)) {
      setConsumerKey(getString(props, prefix, CONSUMER_KEY));
    }
    if (notNull(props, prefix, CONSUMER_SECRET)) {
      setConsumerSecret(getString(props, prefix, CONSUMER_SECRET));
    }

    if (notNull(props, prefix, INITIATOR_NAME)) {
      setInitiatorName(getString(props, prefix, INITIATOR_NAME));
    }
    if (notNull(props, prefix, INITIATOR_SHORTCODE)) {
      setInitiatorShortCode(getString(props, prefix, INITIATOR_SHORTCODE));
    }
    if (notNull(props, prefix, INITIATOR_SECURITY_CREDENTIAL)) {
      setInitiatorSecurityCredential(getString(props, prefix, INITIATOR_SECURITY_CREDENTIAL));
    }

    if (notNull(props, prefix, LIPA_NA_MPESA_SHORTCODE)) {
      setLipaNaMpesaShortCode(getString(props, prefix, LIPA_NA_MPESA_SHORTCODE));
    }
    if (notNull(props, prefix, LIPA_NA_MPESA_PASSKEY)) {
      setLipaNaMpesaPasskey(getString(props, prefix, LIPA_NA_MPESA_PASSKEY));
    }
    if (notNull(props, prefix, LIPA_NA_MPESA_CALLBACK_URL)) {
      setLipaNaMpesaCallbackUrl(getString(props, prefix, LIPA_NA_MPESA_CALLBACK_URL));
    }

    if (notNull(props, prefix, TRANSACTION_REVERSAL_QUEUE_TIMEOUT_URL)) {
      setTransactionReversalQueueTimeoutUrl(
          getString(props, prefix, TRANSACTION_REVERSAL_QUEUE_TIMEOUT_URL));
    }
    if (notNull(props, prefix, TRANSACTION_REVERSAL_RESULT_URL)) {
      setTransactionReversalResultUrl(getString(props, prefix, TRANSACTION_REVERSAL_RESULT_URL));
    }

    if (notNull(props, prefix, TRANSACTION_QUERY_QUEUE_TIMEOUT_URL)) {
      setTransactionQueryQueueTimeoutUrl(
          getString(props, prefix, TRANSACTION_QUERY_QUEUE_TIMEOUT_URL));
    }
    if (notNull(props, prefix, TRANSACTION_QUERY_RESULT_URL)) {
      setTransactionQueryResultUrl(getString(props, prefix, TRANSACTION_QUERY_RESULT_URL));
    }

    cacheInstance();
  }

  private boolean getBoolean(Properties props, String prefix, String name) {
    String value = props.getProperty(prefix + name);
    return Boolean.valueOf(value);
  }

  private int getIntProperty(Properties props, String prefix, String name) {
    String value = props.getProperty(prefix + name);
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException nfe) {
      return -1;
    }
  }

  private String getString(Properties props, String prefix, String name) {
    return props.getProperty(prefix + name);
  }
}
