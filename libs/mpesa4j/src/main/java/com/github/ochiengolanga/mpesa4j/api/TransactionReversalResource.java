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

import com.github.ochiengolanga.mpesa4j.models.responses.BusinessTransactionReversalResponse;
import com.github.ochiengolanga.mpesa4j.models.responses.CustomerTransactionReversalResponse;
import com.github.ochiengolanga.mpesa4j.models.types.Description;
import com.github.ochiengolanga.mpesa4j.models.types.Occasion;
import com.github.ochiengolanga.mpesa4j.models.types.TransactionAmount;
import com.github.ochiengolanga.mpesa4j.models.types.TransactionId;

public interface TransactionReversalResource {
  /**
   * Enables one to reverse a B2B or B2C M-Pesa transaction. <br>
   * <br>
   * see https://developer.safaricom.co.ke/docs#reversal
   *
   * @param transactionId Unique identifier to identify a transaction on M-Pesa
   * @param reversibleAmount Amount to reverse
   * @param description Comments that are sent along with the transaction
   * @param occasion Optional Parameter sequence of characters up to 100
   * @return {@link BusinessTransactionReversalResponse}
   */
  BusinessTransactionReversalResponse reverseBusinessTransaction(
      TransactionId transactionId,
      TransactionAmount reversibleAmount,
      Description description,
      Occasion occasion);

  /**
   * Enables one to reverse a C2B M-Pesa transaction. <br>
   * <br>
   * see https://developer.safaricom.co.ke/docs#reversal
   *
   * @param customerPhoneNumber Customer Phone number
   * @param transactionId Unique identifier to identify a transaction on M-Pesa
   * @param reversibleAmount Amount to reverse
   * @param description Comments that are sent along with the transaction
   * @param occasion Optional Parameter sequence of characters up to 100
   * @return {@link CustomerTransactionReversalResponse}
   */
  CustomerTransactionReversalResponse reverseCustomerTransaction(
      String customerPhoneNumber,
      TransactionId transactionId,
      TransactionAmount reversibleAmount,
      Description description,
      Occasion occasion);
}
