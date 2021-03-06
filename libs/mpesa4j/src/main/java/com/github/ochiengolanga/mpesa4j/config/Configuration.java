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

import com.github.ochiengolanga.mpesa4j.HttpClientConfiguration;
import com.github.ochiengolanga.mpesa4j.auth.AuthorizationConfiguration;
import com.github.ochiengolanga.mpesa4j.models.types.LipaNaMpesaPasskey;
import com.github.ochiengolanga.mpesa4j.models.types.LipaNaMpesaShortCode;

public interface Configuration extends AuthorizationConfiguration, java.io.Serializable {

  HttpClientConfiguration getHttpClientConfiguration();

  boolean isDebugEnabled();

  boolean isSandBoxModeEnabled();

  @Override
  String getConsumerKey();

  @Override
  String getConsumerSecret();

  String getInitiatorName();

  String getInitiatorShortCode();

  String getInitiatorSecurityCredential();

  LipaNaMpesaShortCode getLipaNaMpesaShortCode();

  LipaNaMpesaPasskey getLipaNaMpesaPasskey();

  String getApiBaseUrl();

  String getAccountBalanceApiUrl();

  String getBusinessToCustomerPaymentApiUrl();

  String getCallbackUrlRegistrationApiUrl();

  String getLipaNaMpesaOnlinePaymentUrl();

  String getLipaNaMpesaOnlineQueryUrl();

  String getOAuth2TokenUrl();

  String getTransactionQueryUrl();

  String getTransactionReversalUrl();

  String getAccountBalanceQueueTimeoutUrl();

  String getAccountBalanceResultUrl();

  String getPayBusinessQueueTimeoutUrl();

  String getPayBusinessResultUrl();

  String getPayPromotionQueueTimeoutUrl();

  String getPayPromotionResultUrl();

  String getPaySalaryQueueTimeoutUrl();

  String getPaySalaryResultUrl();

  String getLipaNaMpesaCallbackUrl();

  String getTransactionReversalQueueTimeoutUrl();

  String getTransactionReversalResultUrl();

  String getTransactionQueryQueueTimeoutUrl();

  String getTransactionQueryResultUrl();
}
