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

/**
 * This API enables Business to Business (B2B) transactions between a business and another business.
 * Use of this API requires a valid and verified B2B M-Pesa short code for the business initiating
 * the transaction and the both businesses involved in the transaction.
 *
 * <p>BusinessPayBill, MerchantToMerchantTransfer, MerchantTransferFromMerchantToWorking,
 * MerchantServicesMMFAccountTransfer, AgencyFloatAdvance <br>
 * see https://developer.safaricom.co.ke/docs#b2b-api
 */
public interface TransferResource {}
