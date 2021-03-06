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
package com.github.ochiengolanga.mpesa4j;

import com.github.ochiengolanga.mpesa4j.api.*;

public interface Mpesa
    extends java.io.Serializable,
        AccountBalanceResource,
        ConfigurationResource,
        PaymentResource,
        PaymentRequestResource,
        TransactionQueryResource,
        TransactionReversalResource,
        TransferResource {

  /**
   * AccountBalanceResource
   *
   * @return {@link AccountBalanceResource}
   */
  AccountBalanceResource balances();

  /**
   * ConfigurationResource
   *
   * @return {@link ConfigurationResource}
   */
  ConfigurationResource configurations();

  /**
   * PaymentResource
   *
   * @return {@link PaymentResource}
   */
  PaymentResource payments();

  /**
   * PaymentRequestResource
   *
   * @return {@link PaymentRequestResource}
   */
  PaymentRequestResource paymentRequests();

  /**
   * TransactionQueryResource
   *
   * @return {@link TransactionQueryResource}
   */
  TransactionQueryResource transactionQueries();

  /**
   * TransactionReversalResource
   *
   * @return {@link TransactionReversalResource}
   */
  TransactionReversalResource transactionReversals();

  /**
   * TransferResource
   *
   * @return {@link TransferResource}
   */
  TransferResource transfers();
}
