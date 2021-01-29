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
package com.github.ochiengolanga.mpesa4j.api;

import com.github.ochiengolanga.mpesa4j.models.responses.BusinessTransactionQueryResponse;
import com.github.ochiengolanga.mpesa4j.models.responses.CustomerTransactionQueryResponse;
import com.github.ochiengolanga.mpesa4j.models.types.Description;
import com.github.ochiengolanga.mpesa4j.models.types.Occasion;
import com.github.ochiengolanga.mpesa4j.models.types.TransactionId;

public interface TransactionQueryResource {
  /**
   * PaymentRequest Status API checks the status of a B2B, B2C and C2B APIs transactions. <br>
   * see https://developer.safaricom.co.ke/docs#transaction-status
   *
   * @param transactionID Unique identifier to identify a transaction on M-Pesa
   * @param description Comments that are sent along with the transaction
   * @param occasion Optional Parameter sequence of characters up to 100
   * @return {@link BusinessTransactionQueryResponse}
   */
  BusinessTransactionQueryResponse queryBusinessTransaction(
      TransactionId transactionID, Description description, Occasion occasion);

  /**
   * PaymentRequest Status API checks the status of a B2B, B2C and C2B APIs transactions. <br>
   * see https://developer.safaricom.co.ke/docs#transaction-status
   *
   * @param customerPhoneNumber Customer Phone number
   * @param transactionID Unique identifier to identify a transaction on M-Pesa
   * @param description Comments that are sent along with the transaction
   * @param occasion Optional Parameter sequence of characters up to 100
   * @return {@link CustomerTransactionQueryResponse}
   */
  CustomerTransactionQueryResponse queryCustomerTransaction(
      String customerPhoneNumber,
      TransactionId transactionID,
      Description description,
      Occasion occasion);
}
