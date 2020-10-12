/*
 * Copyright 2019-2020 Daniel Ochieng' Olanga.
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

import com.github.ochiengolanga.mpesa4j.models.responses.BusinessPaymentRequestResponse;
import com.github.ochiengolanga.mpesa4j.models.responses.PromotionPaymentRequestResponse;
import com.github.ochiengolanga.mpesa4j.models.responses.SalaryPaymentRequestResponse;
import java.math.BigDecimal;

public interface PaymentResource {
  /**
   * Enables Business to Customer (B2C) business payment transactions between a business and
   * customers e.g. Bank transfers to mobile. Use of this API requires a valid and verified B2C
   * M-Pesa Short code. <br>
   * <br>
   * see https://developer.safaricom.co.ke/docs?java#b2c-api
   *
   * @param destination
   * @param payableAmount
   * @param description
   * @param occasion
   * @return {@link BusinessPaymentRequestResponse}
   */
  BusinessPaymentRequestResponse payBusiness(
      String destination, BigDecimal payableAmount, String description, String occasion);

  /**
   * Enables Business to Customer (B2C) promotion payment transactions by a company carrying out
   * promotional service e.g. Betting companies paying out winnings to clients. Use of this API
   * requires a valid and verified B2C M-Pesa Short code. <br>
   * <br>
   * see https://developer.safaricom.co.ke/docs?java#b2c-api
   *
   * @param destination
   * @param payableAmount
   * @param description
   * @param occasion
   * @return {@link PromotionPaymentRequestResponse}
   */
  PromotionPaymentRequestResponse payPromotion(
      String destination, BigDecimal payableAmount, String description, String occasion);

  /**
   * Enables Business to Customer (B2C) salary payment transactions between a company and employees
   * who are the end-users of its products or services. Use of this API requires a valid and
   * verified B2C M-Pesa Short code. <br>
   * <br>
   * see https://developer.safaricom.co.ke/docs?java#b2c-api
   *
   * @param destination
   * @param payableAmount
   * @param description
   * @param occasion
   * @return {@link SalaryPaymentRequestResponse}
   */
  SalaryPaymentRequestResponse paySalary(
      String destination, BigDecimal payableAmount, String description, String occasion);
}
