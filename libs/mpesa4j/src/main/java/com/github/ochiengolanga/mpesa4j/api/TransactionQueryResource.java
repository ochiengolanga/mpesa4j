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
package com.github.ochiengolanga.mpesa4j.api;

import com.github.ochiengolanga.mpesa4j.models.responses.BusinessTransactionQueryResponse;
import com.github.ochiengolanga.mpesa4j.models.responses.CustomerTransactionQueryResponse;

public interface TransactionQueryResource {
  /**
   * PaymentRequest Status API checks the status of a B2B, B2C and C2B APIs transactions. <br>
   * see https://developer.safaricom.co.ke/docs#transaction-status
   *
   * @param transactionID
   * @param description
   * @param occasion
   * @return {@link BusinessTransactionQueryResponse}
   */
  BusinessTransactionQueryResponse queryBusinessTransaction(
      String transactionID, String description, String occasion);

  /**
   * PaymentRequest Status API checks the status of a B2B, B2C and C2B APIs transactions. <br>
   * see https://developer.safaricom.co.ke/docs#transaction-status
   *
   * @param customerPhoneNumber
   * @param transactionID
   * @param description
   * @param occasion
   * @return {@link CustomerTransactionQueryResponse}
   */
  CustomerTransactionQueryResponse queryCustomerTransaction(
      String customerPhoneNumber, String transactionID, String description, String occasion);
}
