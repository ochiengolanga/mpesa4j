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

import com.github.ochiengolanga.mpesa4j.HttpClientConfiguration;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
class ConfigurationBase implements Configuration, java.io.Serializable {
  private static final long serialVersionUID = 6175546394599249696L;

  private HttpClientConfiguration httpConf;

  private boolean debug = false;
  private boolean sandBoxModeEnabled = true;

  private String accountBalanceQueueTimeoutUrl = null;
  private String accountBalanceResultUrl = null;

  private String b2bQueueTimeoutUrl = null;
  private String b2bResultUrl = null;

  private String consumerKey = null;
  private String consumerSecret = null;

  private String initiatorName = null;
  private String initiatorShortCode = null;
  private String initiatorSecurityCredential = null;

  private String lipaNaMpesaShortCode = null;
  private String lipaNaMpesaPasskey = null;
  private String lipaNaMpesaCallbackUrl = null;

  private String payBusinessQueueTimeoutUrl = null;
  private String payBusinessResultUrl = null;

  private String payPromotionQueueTimeoutUrl = null;
  private String payPromotionResultUrl = null;

  private String paySalaryQueueTimeoutUrl = null;
  private String paySalaryResultUrl = null;

  private String transactionReversalQueueTimeoutUrl = null;
  private String transactionReversalResultUrl = null;

  private String transactionQueryQueueTimeoutUrl = null;
  private String transactionQueryResultUrl = null;

  private static final String SANDBOX_API_BASE_URL = "https://sandbox.safaricom.co.ke";
  private static final String PRODUCTION_API_BASE_URL = "https://api.safaricom.co.ke";

  private static final String ACCOUNT_BALANCE_API_URL_PATH = "/mpesa/accountbalance/v1/query";
  private static final String BUSINESS_TO_CUSTOMER_PAYMENT_API_URL_PATH =
      "/mpesa/b2c/v1/paymentrequest";
  private static final String CALLBACK_URLS_REGISTRATION_API_URL_PATH = "/mpesa/c2b/v1/registerurl";
  private static final String LIPA_NA_MPESA_ONLINE_PAYMENT_API_URL_PATH =
      "/mpesa/stkpush/v1/processrequest";
  private static final String LIPA_NA_MPESA_ONLINE_QUERY_API_URL_PATH =
      "/mpesa/stkpushquery/v1/query";
  private static final String OAUTH2_TOKEN_API_URL_PATH = "/oauth/v1/generate";
  private static final String TRANSACTION_QUERY_API_URL_PATH = "/mpesa/transactionstatus/v1/query";
  private static final String TRANSACTION_REVERSAL_API_URL_PATH = "/mpesa/reversal/v1/request";

  private String loggerFactory = null;

  protected ConfigurationBase() {
    httpConf = new MyHttpClientConfiguration(20000, 12000);
  }

  @Getter
  @ToString
  @EqualsAndHashCode(callSuper = false)
  static class MyHttpClientConfiguration implements HttpClientConfiguration, java.io.Serializable {
    private static final long serialVersionUID = 8226866124868861058L;
    private int httpConnectionTimeout;
    private int httpReadTimeout;

    MyHttpClientConfiguration(int httpConnectionTimeout, int httpReadTimeout) {
      this.httpConnectionTimeout = httpConnectionTimeout;
      this.httpReadTimeout = httpReadTimeout;
    }

    @Override
    public int getHttpConnectionTimeout() {
      return httpConnectionTimeout;
    }

    @Override
    public int getHttpReadTimeout() {
      return httpReadTimeout;
    }
  }

  @Override
  public HttpClientConfiguration getHttpClientConfiguration() {
    return httpConf;
  }

  protected final void setHttpConnectionTimeout(int connectionTimeout) {
    httpConf = new MyHttpClientConfiguration(connectionTimeout, httpConf.getHttpReadTimeout());
  }

  protected final void setHttpReadTimeout(int readTimeout) {
    httpConf = new MyHttpClientConfiguration(httpConf.getHttpConnectionTimeout(), readTimeout);
  }

  @Override
  public boolean isDebugEnabled() {
    return debug;
  }

  protected final void setDebug(boolean debug) {
    this.debug = debug;
  }

  @Override
  public boolean isSandBoxModeEnabled() {
    return sandBoxModeEnabled;
  }

  protected final void setSandBoxModeEnabled(boolean sandBoxModeEnabled) {
    this.sandBoxModeEnabled = sandBoxModeEnabled;
  }

  @Override
  public String getConsumerKey() {
    return consumerKey;
  }

  protected final void setConsumerKey(String consumerKey) {
    this.consumerKey = consumerKey;
  }

  @Override
  public String getConsumerSecret() {
    return consumerSecret;
  }

  protected final void setConsumerSecret(String consumerSecret) {
    this.consumerSecret = consumerSecret;
  }

  @Override
  public String getInitiatorName() {
    return initiatorName;
  }

  protected final void setInitiatorName(String initiatorName) {
    this.initiatorName = initiatorName;
  }

  @Override
  public String getInitiatorShortCode() {
    return initiatorShortCode;
  }

  protected final void setInitiatorShortCode(String initiatorShortCode) {
    this.initiatorShortCode = initiatorShortCode;
  }

  @Override
  public String getInitiatorSecurityCredential() {
    return initiatorSecurityCredential;
  }

  protected final void setInitiatorSecurityCredential(String securityCredential) {
    this.initiatorSecurityCredential = securityCredential;
  }

  @Override
  public String getLipaNaMpesaShortCode() {
    return lipaNaMpesaShortCode;
  }

  protected final void setLipaNaMpesaShortCode(String lipaNaMpesaShortCode) {
    this.lipaNaMpesaShortCode = lipaNaMpesaShortCode;
  }

  @Override
  public String getLipaNaMpesaPasskey() {
    return lipaNaMpesaPasskey;
  }

  protected final void setLipaNaMpesaPasskey(String lipaNaMpesaPasskey) {
    this.lipaNaMpesaPasskey = lipaNaMpesaPasskey;
  }

  @Override
  public String getApiBaseUrl() {
    if (sandBoxModeEnabled) return SANDBOX_API_BASE_URL;
    else return PRODUCTION_API_BASE_URL;
  }

  @Override
  public String getAccountBalanceApiUrl() {
    return String.format("%s%s", getApiBaseUrl(), ACCOUNT_BALANCE_API_URL_PATH);
  }

  @Override
  public String getBusinessToCustomerPaymentApiUrl() {
    return String.format("%s%s", getApiBaseUrl(), BUSINESS_TO_CUSTOMER_PAYMENT_API_URL_PATH);
  }

  @Override
  public String getCallbackUrlRegistrationApiUrl() {
    return String.format("%s%s", getApiBaseUrl(), CALLBACK_URLS_REGISTRATION_API_URL_PATH);
  }

  @Override
  public String getLipaNaMpesaOnlinePaymentUrl() {
    return String.format("%s%s", getApiBaseUrl(), LIPA_NA_MPESA_ONLINE_PAYMENT_API_URL_PATH);
  }

  @Override
  public String getLipaNaMpesaOnlineQueryUrl() {
    return String.format("%s%s", getApiBaseUrl(), LIPA_NA_MPESA_ONLINE_QUERY_API_URL_PATH);
  }

  @Override
  public String getOAuth2TokenUrl() {
    return String.format("%s%s", getApiBaseUrl(), OAUTH2_TOKEN_API_URL_PATH);
  }

  @Override
  public String getTransactionQueryUrl() {
    return String.format("%s%s", getApiBaseUrl(), TRANSACTION_QUERY_API_URL_PATH);
  }

  @Override
  public String getTransactionReversalUrl() {
    return String.format("%s%s", getApiBaseUrl(), TRANSACTION_REVERSAL_API_URL_PATH);
  }

  @Override
  public String getAccountBalanceQueueTimeoutUrl() {
    return accountBalanceQueueTimeoutUrl;
  }

  protected final void setAccountBalanceQueueTimeoutUrl(String url) {
    this.accountBalanceQueueTimeoutUrl = url;
  }

  @Override
  public String getAccountBalanceResultUrl() {
    return accountBalanceResultUrl;
  }

  protected final void setAccountBalanceResultUrl(String url) {
    this.accountBalanceResultUrl = url;
  }

  @Override
  public String getPayBusinessQueueTimeoutUrl() {
    return payBusinessQueueTimeoutUrl;
  }

  protected final void setPayBusinessQueueTimeoutUrl(String url) {
    this.payBusinessQueueTimeoutUrl = url;
  }

  @Override
  public String getPayBusinessResultUrl() {
    return payBusinessResultUrl;
  }

  protected final void setPayBusinessResultUrl(String url) {
    this.payBusinessResultUrl = url;
  }

  @Override
  public String getPayPromotionQueueTimeoutUrl() {
    return payPromotionQueueTimeoutUrl;
  }

  protected final void setPayPromotionQueueTimeoutUrl(String url) {
    this.payPromotionQueueTimeoutUrl = url;
  }

  @Override
  public String getPayPromotionResultUrl() {
    return payPromotionResultUrl;
  }

  protected final void setPayPromotionResultUrl(String url) {
    this.payPromotionResultUrl = url;
  }

  @Override
  public String getPaySalaryQueueTimeoutUrl() {
    return paySalaryQueueTimeoutUrl;
  }

  protected final void setPaySalaryQueueTimeoutUrl(String url) {
    this.paySalaryQueueTimeoutUrl = url;
  }

  @Override
  public String getPaySalaryResultUrl() {
    return paySalaryResultUrl;
  }

  protected final void setPaySalaryResultUrl(String url) {
    this.paySalaryResultUrl = url;
  }

  @Override
  public String getLipaNaMpesaCallbackUrl() {
    return lipaNaMpesaCallbackUrl;
  }

  protected final void setLipaNaMpesaCallbackUrl(String url) {
    this.lipaNaMpesaCallbackUrl = url;
  }

  @Override
  public String getTransactionReversalQueueTimeoutUrl() {
    return transactionReversalQueueTimeoutUrl;
  }

  protected final void setTransactionReversalQueueTimeoutUrl(String url) {
    this.transactionReversalQueueTimeoutUrl = url;
  }

  @Override
  public String getTransactionReversalResultUrl() {
    return transactionReversalResultUrl;
  }

  protected final void setTransactionReversalResultUrl(String url) {
    this.transactionReversalResultUrl = url;
  }

  @Override
  public String getTransactionQueryQueueTimeoutUrl() {
    return transactionQueryQueueTimeoutUrl;
  }

  protected final void setTransactionQueryQueueTimeoutUrl(String url) {
    this.transactionQueryQueueTimeoutUrl = url;
  }

  @Override
  public String getTransactionQueryResultUrl() {
    return transactionQueryResultUrl;
  }

  protected final void setTransactionQueryResultUrl(String url) {
    this.transactionQueryResultUrl = url;
  }

  private static final List<ConfigurationBase> instances = new ArrayList<>();

  private static void cacheInstance(ConfigurationBase conf) {
    if (!instances.contains(conf)) {
      instances.add(conf);
    }
  }

  protected void cacheInstance() {
    cacheInstance(this);
  }
}
