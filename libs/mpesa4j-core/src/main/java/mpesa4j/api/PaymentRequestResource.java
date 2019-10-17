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
package mpesa4j.api;

import java.math.BigDecimal;
import mpesa4j.models.exceptions.MpesaApiException;
import mpesa4j.models.responses.InstantPaymentQueryResponse;
import mpesa4j.models.responses.InstantPaymentRequestResponse;

public interface PaymentRequestResource {
  /**
   * Enables Business / Organization to initiate transactions via STKPush which eliminates the
   * hustle of having to remember business paybill numbers and account numbers for customers, and
   * allows them to simply confirm the current transaction by entering their M-Pesa PIN on their
   * mobile phone. This is done via the STK Push prompt which appears on a customer's phone that
   * asks them to enter their PIN. <br>
   * https://developer.safaricom.co.ke/docs#lipa-na-m-pesa-online-payment
   *
   * @param customerPhoneNumber
   * @param payableAmount
   * @param accountReference
   * @param description
   * @return {@link InstantPaymentRequestResponse}
   * @throws MpesaApiException
   */
  InstantPaymentRequestResponse requestInstantPayment(
      String customerPhoneNumber,
      BigDecimal payableAmount,
      String accountReference,
      String description)
      throws MpesaApiException;

  /**
   * Enables Business / Organization to query transactions initiated via STKPush. <br>
   * https://developer.safaricom.co.ke/docs#lipa-na-m-pesa-online-query-request
   *
   * @param paymentId
   * @return {@link InstantPaymentQueryResponse}
   * @throws MpesaApiException
   */
  InstantPaymentQueryResponse queryInstantPayment(String paymentId) throws MpesaApiException;
}
