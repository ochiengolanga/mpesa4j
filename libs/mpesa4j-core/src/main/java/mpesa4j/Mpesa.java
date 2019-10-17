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
package mpesa4j;

import mpesa4j.api.*;
import mpesa4j.auth.OAuth2Support;

public interface Mpesa
    extends java.io.Serializable,
        OAuth2Support,
        AccountBalanceResource,
        ConfigurationResource,
        PaymentResource,
        PaymentRequestResource,
        TransactionQueryResource,
        TransactionReversalResource,
        TransferResource {

  /** @return {@link mpesa4j.api.AccountBalanceResource} */
  AccountBalanceResource balances();

  /** @return {@link mpesa4j.api.ConfigurationResource} */
  ConfigurationResource configurations();

  /** @return {@link mpesa4j.api.PaymentResource} */
  PaymentResource payments();

  /** @return {@link PaymentRequestResource} */
  PaymentRequestResource paymentRequests();

  /** @return {@link mpesa4j.api.TransactionQueryResource} */
  TransactionQueryResource transactionQueries();

  /** @return {@link mpesa4j.api.TransactionReversalResource} */
  TransactionReversalResource transactionReversals();

  /** @return {@link mpesa4j.api.TransferResource} */
  TransferResource transfers();
}
