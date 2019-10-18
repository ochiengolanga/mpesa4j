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

import java.math.BigDecimal;
import com.github.ochiengolanga.mpesa4j.models.exceptions.MpesaApiException;
import com.github.ochiengolanga.mpesa4j.models.responses.BusinessTransactionReversalResponse;
import com.github.ochiengolanga.mpesa4j.models.responses.CustomerTransactionReversalResponse;

public interface TransactionReversalResource {
  /**
   * Enables one to reverse a B2B or B2C M-Pesa transaction. <br>
   * <br>
   * see https://developer.safaricom.co.ke/docs#reversal
   *
   * @param transactionId
   * @param reversibleAmount
   * @param description
   * @param occasion
   * @return {@link BusinessTransactionReversalResponse}
   * @throws MpesaApiException
   */
  BusinessTransactionReversalResponse reverseBusinessTransaction(
      String transactionId, BigDecimal reversibleAmount, String description, String occasion)
      throws MpesaApiException;

  /**
   * Enables one to reverse a C2B M-Pesa transaction. <br>
   * <br>
   * see https://developer.safaricom.co.ke/docs#reversal
   *
   * @param customerPhoneNumber
   * @param transactionId
   * @param reversibleAmount
   * @param description
   * @param occasion
   * @return {@link CustomerTransactionReversalResponse}
   * @throws MpesaApiException
   */
  CustomerTransactionReversalResponse reverseCustomerTransaction(
      String customerPhoneNumber,
      String transactionId,
      BigDecimal reversibleAmount,
      String description,
      String occasion)
      throws MpesaApiException;
}
