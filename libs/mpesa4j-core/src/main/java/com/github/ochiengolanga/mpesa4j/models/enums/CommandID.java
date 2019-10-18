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
package com.github.ochiengolanga.mpesa4j.models.enums;

import lombok.Getter;

/**
 * <br>
 * https://developer.safaricom.co.ke/docs#command-ids
 */
@Getter
public enum CommandID {
  ACCOUNT_BALANCE("AccountBalance"),
  BUSINESS_BUY_GOODS("BusinessBuyGoods"),
  BUSINESS_PAYMENT("BusinessPayment"),
  BUSINESS_PAYBILL("BusinessPayBill"),
  BUSINESS_TO_BUSINESS_TRANSFER("BusinessToBusinessTransfer"),
  BUSINESS_TRANSFER_FROM_MMF_TO_UTILITY("BusinessTransferFromMMFToUtility"),
  CHECK_IDENTITY("CheckIdentity"),
  CUSTOMER_BUY_GOODS_ONLINE("CustomerBuyGoodsOnline"),
  CUSTOMER_PAY_BILL_ONLINE("CustomerPayBillOnline"),
  DISBURSE_FUNDS_TO_BUSINESS("DisburseFundsToBusiness"),
  PROMOTION_PAYMENT("PromotionPayment"),
  SALARY_PAYMENT("SalaryPayment"),
  TRANSACTION_STATUS_QUERY("TransactionStatusQuery"),
  TRANSACTION_REVERSAL("TransactionReversal");

  private final String commandId;

  CommandID(String commandId) {
    this.commandId = commandId;
  }
}
