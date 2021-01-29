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

import com.github.ochiengolanga.mpesa4j.models.responses.AccountBalanceResponse;
import com.github.ochiengolanga.mpesa4j.models.types.Description;

public interface AccountBalanceResource {
  /**
   * The Account Balance API requests for the account balance of a shortcode. <br>
   * see https://developer.safaricom.co.ke/docs#account-balance-api
   *
   * @param description Comments that are sent along with the transaction.
   * @return {@link AccountBalanceResponse}
   */
  AccountBalanceResponse queryBalance(Description description);
}
