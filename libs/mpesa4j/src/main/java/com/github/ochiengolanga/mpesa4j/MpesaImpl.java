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
package com.github.ochiengolanga.mpesa4j;

import static com.github.ochiengolanga.mpesa4j.util.Preconditions.checkEmptyString;
import static com.github.ochiengolanga.mpesa4j.util.Preconditions.checkNotNull;

import com.github.ochiengolanga.mpesa4j.api.*;
import com.github.ochiengolanga.mpesa4j.auth.Authorization;
import com.github.ochiengolanga.mpesa4j.config.Configuration;
import com.github.ochiengolanga.mpesa4j.models.ApiResource;
import com.github.ochiengolanga.mpesa4j.models.enums.CommandID;
import com.github.ochiengolanga.mpesa4j.models.enums.DefaultAction;
import com.github.ochiengolanga.mpesa4j.models.enums.IdentifierType;
import com.github.ochiengolanga.mpesa4j.models.requests.*;
import com.github.ochiengolanga.mpesa4j.models.responses.*;
import com.github.ochiengolanga.mpesa4j.models.types.*;
import com.github.ochiengolanga.mpesa4j.util.GenerationUtils;
import io.vavr.control.Try;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A java representation of the <a href="https://developer.safaricom.co.ke/docs">Safaricom M-Pesa
 * Daraja API</a><br>
 * This class is thread safe and can be cached/re-used and used concurrently.<br>
 */
class MpesaImpl extends MpesaBaseImpl implements Mpesa {
  private static final long serialVersionUID = 9170943084096085770L;

  private static final Logger LOG = LoggerFactory.getLogger(MpesaImpl.class);

  MpesaImpl(Configuration conf, Authorization auth) {
    super(conf, auth);
  }

  @Override
  public InstantPaymentRequestResponse requestInstantPayment(
      @NonNull String customerPhoneNumber,
      @NonNull TransactionAmount payableAmount,
      @NonNull AccountReference accountReference,
      Description description) {

    checkInstantPayPreconditions(conf.getLipaNaMpesaShortCode(), conf.getLipaNaMpesaPasskey());

    checkNotNull(conf.getLipaNaMpesaCallbackUrl(), "LipaNaMpesa callback URL missing");
    checkEmptyString(conf.getLipaNaMpesaCallbackUrl(), "LipaNaMpesa callback URL missing");
    checkNotNull(accountReference, "Account reference is missing");
    checkNotNull(description, "Description is missing.");

    return Try.of(
            () ->
                createInstantPaymentRequest(
                    conf.getLipaNaMpesaShortCode(),
                    conf.getLipaNaMpesaPasskey(),
                    customerPhoneNumber,
                    payableAmount,
                    accountReference,
                    description,
                    conf.getLipaNaMpesaCallbackUrl()))
        .map(req -> post(conf.getLipaNaMpesaOnlinePaymentUrl(), req.toJson()))
        .map(
            res ->
                new InstantPaymentRequestResponse(
                    res.getMerchantRequestId(),
                    res.getCheckoutRequestId(),
                    res.getResponseCode(),
                    res.getResponseDescription(),
                    res.getCustomerMessage()))
        .get();
  }

  @Override
  public InstantPaymentQueryResponse queryInstantPayment(PaymentId paymentId) {
    checkInstantPayPreconditions(conf.getLipaNaMpesaShortCode(), conf.getLipaNaMpesaPasskey());
    checkNotNull(paymentId, "Payment Id missing");

    return Try.of(
            () ->
                createPaymentQueryRequest(
                    conf.getLipaNaMpesaShortCode(), conf.getLipaNaMpesaPasskey(), paymentId))
        .map(req -> post(conf.getLipaNaMpesaOnlineQueryUrl(), req.toJson()))
        .map(
            res ->
                new InstantPaymentQueryResponse(
                    res.getMerchantRequestId(),
                    res.getCheckoutRequestId(),
                    res.getResponseCode(),
                    res.getResponseDescription(),
                    res.getResultCode(),
                    res.getResultDescription()))
        .get();
  }

  @Override
  public BusinessPaymentRequestResponse payBusiness(
      @NonNull PhoneNumber payee,
      @NonNull TransactionAmount payableAmount,
      @NonNull Description description,
      Occasion occasion) {

    checkInitiatorPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential());

    checkNotNull(conf.getPayBusinessQueueTimeoutUrl(), "Queue timeout url missing");
    checkEmptyString(conf.getPayBusinessQueueTimeoutUrl(), "Queue timeout url missing");
    checkNotNull(conf.getPayBusinessResultUrl(), "Result url missing");
    checkEmptyString(conf.getPayBusinessResultUrl(), "Result url missing");
    checkNotNull(description, "Description is missing");

    return Try.of(
            () ->
                createPayBusinessRequest(
                    conf.getInitiatorShortCode(),
                    conf.getInitiatorName(),
                    conf.getInitiatorSecurityCredential(),
                    payee,
                    payableAmount,
                    description,
                    occasion,
                    conf.getPayBusinessQueueTimeoutUrl(),
                    conf.getPayBusinessResultUrl()))
        .map(req -> post(conf.getBusinessToCustomerPaymentApiUrl(), req.toJson()))
        .map(
            res ->
                new BusinessPaymentRequestResponse(
                    res.getConversationId(),
                    res.getOriginatorConversationId(),
                    res.getResponseCode(),
                    res.getResponseDescription()))
        .get();
  }

  @Override
  public PromotionPaymentRequestResponse payPromotion(
      @NonNull PhoneNumber payee,
      @NonNull TransactionAmount payableAmount,
      @NonNull Description description,
      Occasion occasion) {
    checkInitiatorPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential());

    checkNotNull(conf.getPayPromotionQueueTimeoutUrl(), "Queue timeout url missing");
    checkEmptyString(conf.getPayPromotionQueueTimeoutUrl(), "Queue timeout url missing");
    checkNotNull(conf.getPayPromotionResultUrl(), "Result url missing");
    checkEmptyString(conf.getPayPromotionResultUrl(), "Result url missing");
    checkNotNull(description, "Description is missing");

    return Try.of(
            () ->
                createPayPromotionRequest(
                    conf.getInitiatorShortCode(),
                    conf.getInitiatorName(),
                    conf.getInitiatorSecurityCredential(),
                    payee,
                    payableAmount,
                    description,
                    occasion,
                    conf.getPayPromotionQueueTimeoutUrl(),
                    conf.getPayPromotionResultUrl()))
        .map(req -> post(conf.getBusinessToCustomerPaymentApiUrl(), req.toJson()))
        .map(
            res ->
                new PromotionPaymentRequestResponse(
                    res.getConversationId(),
                    res.getOriginatorConversationId(),
                    res.getResponseCode(),
                    res.getResponseDescription()))
        .get();
  }

  @Override
  public SalaryPaymentRequestResponse paySalary(
      @NonNull PhoneNumber payee,
      @NonNull TransactionAmount payableAmount,
      @NonNull Description description,
      Occasion occasion) {
    checkInitiatorPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential());

    checkNotNull(conf.getPaySalaryQueueTimeoutUrl(), "Queue timeout url missing");
    checkEmptyString(conf.getPaySalaryQueueTimeoutUrl(), "Queue timeout url missing");
    checkNotNull(conf.getPaySalaryResultUrl(), "Result url missing");
    checkEmptyString(conf.getPaySalaryResultUrl(), "Result url missing");
    checkNotNull(description, "Description is missing");

    return Try.of(
            () ->
                createPaySalaryRequest(
                    conf.getInitiatorShortCode(),
                    conf.getInitiatorName(),
                    conf.getInitiatorSecurityCredential(),
                    payee,
                    payableAmount,
                    description,
                    occasion,
                    conf.getPaySalaryQueueTimeoutUrl(),
                    conf.getPaySalaryResultUrl()))
        .map(req -> post(conf.getBusinessToCustomerPaymentApiUrl(), req.toJson()))
        .map(
            res ->
                new SalaryPaymentRequestResponse(
                    res.getConversationId(),
                    res.getOriginatorConversationId(),
                    res.getResponseCode(),
                    res.getResponseDescription()))
        .get();
  }

  @Override
  public BusinessTransactionQueryResponse queryBusinessTransaction(
      TransactionId transactionID, Description description, Occasion occasion) {
    checkTransactionQueryPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential(),
        conf.getTransactionQueryQueueTimeoutUrl(),
        conf.getTransactionQueryResultUrl());
    checkNotNull(transactionID, "Transaction ID is missing");
    checkNotNull(description, "Description is missing.");

    return Try.of(
            () ->
                createBusinessTransactionQueryRequest(
                    conf.getInitiatorName(),
                    conf.getInitiatorShortCode(),
                    conf.getInitiatorSecurityCredential(),
                    transactionID,
                    description,
                    occasion,
                    conf.getTransactionQueryQueueTimeoutUrl(),
                    conf.getTransactionQueryResultUrl()))
        .map(req -> post(conf.getTransactionQueryUrl(), req.toJson()))
        .map(
            res ->
                new BusinessTransactionQueryResponse(
                    res.getConversationId(),
                    res.getOriginatorConversationId(),
                    res.getResponseCode(),
                    res.getResponseDescription()))
        .get();
  }

  @Override
  public CustomerTransactionQueryResponse queryCustomerTransaction(
      String customerPhoneNumber,
      TransactionId transactionID,
      Description description,
      Occasion occasion) {
    checkTransactionQueryPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential(),
        conf.getTransactionQueryQueueTimeoutUrl(),
        conf.getTransactionQueryResultUrl());
    checkNotNull(description, "Description is missing.");
    checkNotNull(transactionID, "Transaction ID is missing");

    return Try.of(
            () ->
                createCustomerTransactionQueryRequest(
                    conf.getInitiatorName(),
                    conf.getInitiatorSecurityCredential(),
                    customerPhoneNumber,
                    transactionID,
                    description,
                    occasion,
                    conf.getTransactionQueryQueueTimeoutUrl(),
                    conf.getTransactionQueryResultUrl()))
        .map(req -> post(conf.getTransactionQueryUrl(), req.toJson()))
        .map(
            res ->
                new CustomerTransactionQueryResponse(
                    res.getConversationId(),
                    res.getOriginatorConversationId(),
                    res.getResponseCode(),
                    res.getResponseDescription()))
        .get();
  }

  @Override
  public CallbackUrlsRegistrationResponse registerCallbackUrls(
      DefaultAction defaultAction, ValidationUrl validationUrl, ConfirmationUrl confirmationUrl) {
    checkInitiatorPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential());

    return Try.of(
            () ->
                createCallbackUrlRegistrationRequest(
                    conf.getInitiatorShortCode(), defaultAction, validationUrl, confirmationUrl))
        .map(req -> post(conf.getCallbackUrlRegistrationApiUrl(), req.toJson()))
        .map(
            res ->
                new CallbackUrlsRegistrationResponse(
                    res.getConversationId(),
                    res.getOriginatorConversationId(),
                    res.getResponseDescription()))
        .get();
  }

  @Override
  public BusinessTransactionReversalResponse reverseBusinessTransaction(
      TransactionId transactionId,
      TransactionAmount amount,
      Description description,
      Occasion occasion) {
    checkReverseTransactionPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential(),
        conf.getTransactionReversalQueueTimeoutUrl(),
        conf.getTransactionReversalResultUrl());
    checkNotNull(transactionId, "Transaction ID is missing");

    return Try.of(
            () ->
                createBusinessTransactionReversalRequest(
                    conf.getInitiatorName(),
                    conf.getInitiatorShortCode(),
                    conf.getInitiatorSecurityCredential(),
                    transactionId,
                    amount,
                    description,
                    occasion,
                    conf.getTransactionReversalQueueTimeoutUrl(),
                    conf.getTransactionReversalResultUrl()))
        .map(req -> post(conf.getTransactionReversalUrl(), req.toJson()))
        .map(
            res ->
                new BusinessTransactionReversalResponse(
                    res.getConversationId(),
                    res.getOriginatorConversationId(),
                    res.getResponseCode(),
                    res.getResponseDescription()))
        .get();
  }

  @Override
  public CustomerTransactionReversalResponse reverseCustomerTransaction(
      String customerPhoneNumber,
      TransactionId transactionId,
      TransactionAmount amount,
      Description description,
      Occasion occasion) {
    checkReverseTransactionPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential(),
        conf.getTransactionReversalQueueTimeoutUrl(),
        conf.getTransactionReversalResultUrl());
    checkNotNull(description, "Transaction ID is missing");
    checkNotNull(transactionId, "Transaction ID is missing");

    return Try.of(
            () ->
                createCustomerTransactionReversalRequest(
                    conf.getInitiatorName(),
                    conf.getInitiatorSecurityCredential(),
                    customerPhoneNumber,
                    transactionId,
                    amount,
                    description,
                    occasion,
                    conf.getTransactionReversalQueueTimeoutUrl(),
                    conf.getTransactionReversalResultUrl()))
        .map(req -> post(conf.getTransactionReversalUrl(), req.toJson()))
        .map(
            res ->
                new CustomerTransactionReversalResponse(
                    res.getConversationId(),
                    res.getOriginatorConversationId(),
                    res.getResponseCode(),
                    res.getResponseDescription()))
        .get();
  }

  @Override
  public AccountBalanceResponse queryBalance(Description description) {
    checkInitiatorPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential());

    checkNotNull(
        conf.getAccountBalanceQueueTimeoutUrl(), "Account balance queue timeout url missing");
    checkEmptyString(
        conf.getAccountBalanceQueueTimeoutUrl(), "Account balance queue timeout url missing");
    checkNotNull(conf.getAccountBalanceResultUrl(), "Account balance result url missing");
    checkEmptyString(conf.getAccountBalanceResultUrl(), "Account balance result url missing");
    checkNotNull(description, "Description is missing.");

    return Try.of(
            () ->
                createAccountBalanceRequest(
                    conf.getInitiatorName(),
                    conf.getInitiatorShortCode(),
                    conf.getInitiatorSecurityCredential(),
                    description,
                    conf.getAccountBalanceQueueTimeoutUrl(),
                    conf.getAccountBalanceResultUrl()))
        .map(req -> post(conf.getAccountBalanceApiUrl(), req.toJson()))
        .map(
            res ->
                new AccountBalanceResponse(
                    res.getConversationId(),
                    res.getOriginatorConversationId(),
                    res.getResponseCode(),
                    res.getResponseDescription()))
        .get();
  }

  @Override
  public AccountBalanceResource balances() {
    return this;
  }

  @Override
  public ConfigurationResource configurations() {
    return this;
  }

  @Override
  public PaymentResource payments() {
    return this;
  }

  @Override
  public PaymentRequestResource paymentRequests() {
    return this;
  }

  @Override
  public TransactionQueryResource transactionQueries() {
    return this;
  }

  @Override
  public TransactionReversalResource transactionReversals() {
    return this;
  }

  @Override
  public TransferResource transfers() {
    return this;
  }

  private static AccountBalanceRequest createAccountBalanceRequest(
      String initiatorName,
      String initiatorShortCode,
      String initiatorSecurityCredential,
      Description description,
      String queueTimeoutUrl,
      String resultUrl) {
    checkNotNull(description, "Description missing");

    return AccountBalanceRequest.builder()
        .initiatorName(initiatorName)
        .businessShortCode(initiatorShortCode)
        .credentials(initiatorSecurityCredential)
        .transactionType(CommandID.ACCOUNT_BALANCE.getCommandId())
        .identifierType(IdentifierType.SHORTCODE.getIdentifierType())
        .description(description.getValue())
        .queueTimeoutUrl(queueTimeoutUrl)
        .resultUrl(resultUrl)
        .build();
  }

  private static CallbackUrlRegistrationRequest createCallbackUrlRegistrationRequest(
      String shortCode,
      DefaultAction defaultAction,
      ValidationUrl validationUrl,
      ConfirmationUrl confirmationUrl) {

    checkNotNull(defaultAction, "Default action missing");
    checkNotNull(validationUrl, "Validation Url missing");
    checkNotNull(confirmationUrl, "Confirmation Url missing");

    return CallbackUrlRegistrationRequest.builder()
        .shortCode(shortCode)
        .callbackType(defaultAction.getDefaultAction())
        .validationUrl(validationUrl.getValue())
        .confirmationUrl(confirmationUrl.getValue())
        .build();
  }

  private static InstantPaymentQueryRequest createPaymentQueryRequest(
      LipaNaMpesaShortCode shortCode, LipaNaMpesaPasskey passkey, PaymentId paymentId) {
    checkNotNull(paymentId, "PaymentID missing");
    checkNotNull(paymentId, "PaymentID missing");

    String timestamp = GenerationUtils.generateTimestamp();

    return InstantPaymentQueryRequest.builder()
        .businessShortCode(shortCode.getValue())
        .password(GenerationUtils.generatePassword(shortCode, passkey, timestamp))
        .timestamp(timestamp)
        .paymentId(paymentId.getValue())
        .build();
  }

  private static InstantPaymentRequest createInstantPaymentRequest(
      LipaNaMpesaShortCode shortCode,
      LipaNaMpesaPasskey passkey,
      String customerPhoneNumber,
      TransactionAmount payableAmount,
      AccountReference accountReference,
      Description description,
      String callbackUrl) {
    checkNotNull(customerPhoneNumber, "Customer phone number missing");
    checkEmptyString(customerPhoneNumber, "Customer phone number missing");
    checkNotNull(accountReference, "Account reference missing");
    checkNotNull(accountReference, "Account reference missing");
    checkNotNull(description, "Description missing");
    checkNotNull(payableAmount, "Payable amount missing");

    String timestamp = GenerationUtils.generateTimestamp();

    return InstantPaymentRequest.builder()
        .businessShortCode(shortCode.getValue())
        .password(GenerationUtils.generatePassword(shortCode, passkey, timestamp))
        .timestamp(timestamp)
        .transactionType(CommandID.CUSTOMER_PAY_BILL_ONLINE.getCommandId())
        .amount(payableAmount.getValue().toString())
        .customerPhoneNumber(customerPhoneNumber)
        .initiatorShortCode(shortCode.getValue())
        .phoneNumber(customerPhoneNumber)
        .callbackUrl(callbackUrl)
        .accountReference(accountReference.getValue())
        .transactionDescription(description.getValue())
        .build();
  }

  private static PaymentRequest createPayBusinessRequest(
      String initiatorShortCode,
      String initiatorName,
      String initiatorSecurityCredential,
      PhoneNumber payee,
      TransactionAmount payableAmount,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    return createPaymentRequest(
        CommandID.BUSINESS_PAYMENT,
        initiatorShortCode,
        initiatorName,
        initiatorSecurityCredential,
        payee,
        payableAmount,
        description,
        occasion,
        queueTimeoutUrl,
        resultUrl);
  }

  private static PaymentRequest createPayPromotionRequest(
      String initiatorShortCode,
      String initiatorName,
      String initiatorSecurityCredential,
      PhoneNumber payee,
      TransactionAmount payableAmount,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    return createPaymentRequest(
        CommandID.PROMOTION_PAYMENT,
        initiatorShortCode,
        initiatorName,
        initiatorSecurityCredential,
        payee,
        payableAmount,
        description,
        occasion,
        queueTimeoutUrl,
        resultUrl);
  }

  private static PaymentRequest createPaySalaryRequest(
      String initiatorShortCode,
      String initiatorName,
      String initiatorSecurityCredential,
      PhoneNumber payee,
      TransactionAmount payableAmount,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    return createPaymentRequest(
        CommandID.SALARY_PAYMENT,
        initiatorShortCode,
        initiatorName,
        initiatorSecurityCredential,
        payee,
        payableAmount,
        description,
        occasion,
        queueTimeoutUrl,
        resultUrl);
  }

  private static PaymentRequest createPaymentRequest(
      CommandID transactionType,
      String initiatorShortCode,
      String initiatorName,
      String initiatorSecurityCredential,
      PhoneNumber payee,
      TransactionAmount amount,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    checkNotNull(payee, "Customer phone number missing");
    checkEmptyString(payee.getValue(), "Customer phone number missing");
    checkNotNull(description, "Description missing");
    checkNotNull(amount, "Payable amount missing");

    String requestOccasion = "";
    if (occasion != null) {
      requestOccasion = occasion.getValue();
    }

    return PaymentRequest.builder()
        .initiatorName(initiatorName)
        .credentials(initiatorSecurityCredential)
        .transactionType(transactionType.getCommandId())
        .amount(amount.getValue().toString())
        .initiatorShortCode(initiatorShortCode)
        .payee(payee.getValue())
        .description(description.getValue())
        .queueTimeoutUrl(queueTimeoutUrl)
        .resultUrl(resultUrl)
        .occasion(requestOccasion)
        .build();
  }

  private static TransactionQueryRequest createBusinessTransactionQueryRequest(
      String initiatorName,
      String initiatorShortCode,
      String initiatorSecurityCredential,
      TransactionId transactionID,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    return createTransactionQuery(
        IdentifierType.SHORTCODE,
        initiatorName,
        initiatorSecurityCredential,
        initiatorShortCode,
        transactionID,
        description,
        occasion,
        queueTimeoutUrl,
        resultUrl);
  }

  private static TransactionQueryRequest createCustomerTransactionQueryRequest(
      String initiatorName,
      String initiatorSecurityCredential,
      String customerPhoneNumber,
      TransactionId transactionID,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    checkNotNull(customerPhoneNumber, "Customer phone number missing");
    checkEmptyString(customerPhoneNumber, "Customer phone number missing");

    return createTransactionQuery(
        IdentifierType.MSISDN,
        initiatorName,
        initiatorSecurityCredential,
        customerPhoneNumber,
        transactionID,
        description,
        occasion,
        queueTimeoutUrl,
        resultUrl);
  }

  private static TransactionQueryRequest createTransactionQuery(
      IdentifierType identifierType,
      String initiatorName,
      String initiatorSecurityCredential,
      String sender,
      TransactionId transactionID,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    checkNotNull(transactionID, "TransactionID missing");
    checkNotNull(description, "Description missing");

    return TransactionQueryRequest.builder()
        .initiatorName(initiatorName)
        .credentials(initiatorSecurityCredential)
        .transactionType(CommandID.TRANSACTION_STATUS_QUERY.getCommandId())
        .identifierType(identifierType.getIdentifierType())
        .transactionID(transactionID.getValue())
        .sender(sender)
        .description(description.getValue())
        .occasion(occasion.getValue())
        .queueTimeoutUrl(queueTimeoutUrl)
        .resultUrl(resultUrl)
        .build();
  }

  private static TransactionReversalRequest createBusinessTransactionReversalRequest(
      String initiatorName,
      String initiatorShortCode,
      String initiatorSecurityCredential,
      TransactionId transactionId,
      TransactionAmount amount,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    return createTransactionReversalRequest(
        IdentifierType.SHORTCODE,
        initiatorName,
        initiatorSecurityCredential,
        initiatorShortCode,
        transactionId,
        amount,
        description,
        occasion,
        queueTimeoutUrl,
        resultUrl);
  }

  private static TransactionReversalRequest createCustomerTransactionReversalRequest(
      String initiatorName,
      String initiatorSecurityCredential,
      String customerPhoneNumber,
      TransactionId transactionId,
      TransactionAmount amount,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    checkNotNull(customerPhoneNumber, "Customer phone number missing");
    checkEmptyString(customerPhoneNumber, "Customer phone number missing");

    return createTransactionReversalRequest(
        IdentifierType.MSISDN,
        initiatorName,
        initiatorSecurityCredential,
        customerPhoneNumber,
        transactionId,
        amount,
        description,
        occasion,
        queueTimeoutUrl,
        resultUrl);
  }

  private static TransactionReversalRequest createTransactionReversalRequest(
      IdentifierType identifierType,
      String initiatorName,
      String initiatorSecurityCredential,
      String receiver,
      TransactionId transactionId,
      TransactionAmount amount,
      Description description,
      Occasion occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    checkNotNull(transactionId, "TransactionID missing");
    checkNotNull(description, "Description missing");
    checkNotNull(amount, "Amount to reverse missing");

    String requestOccasion = "";
    if (occasion != null) {
      requestOccasion = occasion.getValue();
    }

    return TransactionReversalRequest.builder()
        .initiatorName(initiatorName)
        .receiverParty(receiver)
        .receiverIdentifierType(identifierType.getIdentifierType())
        .credentials(initiatorSecurityCredential)
        .transactionType(CommandID.TRANSACTION_REVERSAL.getCommandId())
        .transactionID(transactionId.getValue())
        .amount(amount.getValue().toString())
        .description(description.getValue())
        .occasion(requestOccasion)
        .queueTimeoutUrl(queueTimeoutUrl)
        .resultUrl(resultUrl)
        .build();
  }

  private MpesaResponse post(String url, String json) {
    HttpParameter[] parameters = new HttpParameter[1];
    parameters[0] = new HttpParameter("payload", json);

    return executeRequest(ApiResource.RequestMethod.POST, url, parameters);
  }

  private MpesaResponse executeRequest(
      ApiResource.RequestMethod method, String url, HttpParameter[] parameters) {
    return Try.of(() -> http.request(method, url, auth.getBearerAuthorizationHeader(), parameters))
        .onFailure(exc -> LOG.error("{}", exc.getMessage()))
        .get();
  }

  private static void checkInitiatorPreconditions(
      String name, String shortCode, String securityCredential) {
    checkNotNull(name, "Initiator name missing");
    checkEmptyString(name, "Initiator name missing");
    checkNotNull(shortCode, "Initiator code missing");
    checkEmptyString(shortCode, "Initiator code missing");
    checkNotNull(securityCredential, "Initiator security credential missing");
    checkEmptyString(securityCredential, "Initiator security credential missing");
  }

  private static void checkInstantPayPreconditions(
      LipaNaMpesaShortCode shortCode, LipaNaMpesaPasskey passkey) {
    checkNotNull(shortCode, "LipaNaMpesa shortcode missing");
    checkEmptyString(shortCode.getValue(), "LipaNaMpesa shortcode missing");
    checkNotNull(passkey, "LipaNaMpesa passkey missing");
    checkEmptyString(passkey.getValue(), "LipaNaMpesa passkey missing");
  }

  private static void checkTransactionQueryPreconditions(
      String initiatorName,
      String initiatorShortCode,
      String securityCredential,
      String queueTimeoutUrl,
      String resultUrl) {
    checkInitiatorPreconditions(initiatorName, initiatorShortCode, securityCredential);

    checkNotNull(queueTimeoutUrl, "PaymentRequest query queue timeout url missing");
    checkEmptyString(queueTimeoutUrl, "PaymentRequest query queue timeout url missing");
    checkNotNull(resultUrl, "PaymentRequest query result url missing");
    checkEmptyString(resultUrl, "PaymentRequest query result url missing");
  }

  private static void checkReverseTransactionPreconditions(
      String initiatorName,
      String initiatorShortCode,
      String securityCredential,
      String queueTimeoutUrl,
      String resultUrl) {
    checkInitiatorPreconditions(initiatorName, initiatorShortCode, securityCredential);

    checkNotNull(queueTimeoutUrl, "Transaction reversal queue timeout url missing");
    checkEmptyString(queueTimeoutUrl, "Transaction reversal queue timeout url missing");
    checkNotNull(resultUrl, "Transaction reversal result url missing");
    checkEmptyString(resultUrl, "Transaction reversal result url missing");
  }
}
