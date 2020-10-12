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
package com.github.ochiengolanga.mpesa4j;

import com.github.ochiengolanga.mpesa4j.api.*;
import com.github.ochiengolanga.mpesa4j.auth.Authorization;
import com.github.ochiengolanga.mpesa4j.config.Configuration;
import com.github.ochiengolanga.mpesa4j.models.ApiResource;
import com.github.ochiengolanga.mpesa4j.models.enums.CommandID;
import com.github.ochiengolanga.mpesa4j.models.enums.DefaultAction;
import com.github.ochiengolanga.mpesa4j.models.enums.IdentifierType;
import com.github.ochiengolanga.mpesa4j.models.requests.*;
import com.github.ochiengolanga.mpesa4j.models.responses.*;
import com.github.ochiengolanga.mpesa4j.util.GenerationUtils;
import com.github.ochiengolanga.mpesa4j.util.Preconditions;
import io.vavr.control.Try;
import java.math.BigDecimal;
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
      String customerPhoneNumber,
      BigDecimal payableAmount,
      String accountReference,
      String description) {
    checkInstantPayPreconditions(conf.getLipaNaMpesaShortCode(), conf.getLipaNaMpesaPasskey());

    Preconditions.checkNotNull(
        conf.getLipaNaMpesaCallbackUrl(), "LipaNaMpesa callback URL missing");
    Preconditions.checkEmptyString(
        conf.getLipaNaMpesaCallbackUrl(), "LipaNaMpesa callback URL missing");

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
  public InstantPaymentQueryResponse queryInstantPayment(String paymentId) {
    checkInstantPayPreconditions(conf.getLipaNaMpesaShortCode(), conf.getLipaNaMpesaPasskey());

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
      String destination, BigDecimal payableAmount, String description, String occasion) {
    checkInitiatorPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential());

    Preconditions.checkNotNull(conf.getPayBusinessQueueTimeoutUrl(), "Queue timeout url missing");
    Preconditions.checkEmptyString(
        conf.getPayBusinessQueueTimeoutUrl(), "Queue timeout url missing");
    Preconditions.checkNotNull(conf.getPayBusinessResultUrl(), "Result url missing");
    Preconditions.checkEmptyString(conf.getPayBusinessResultUrl(), "Result url missing");

    return Try.of(
            () ->
                createPayBusinessRequest(
                    conf.getInitiatorShortCode(),
                    conf.getInitiatorName(),
                    conf.getInitiatorSecurityCredential(),
                    destination,
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
      String destination, BigDecimal payableAmount, String description, String occasion) {
    checkInitiatorPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential());

    Preconditions.checkNotNull(conf.getPayPromotionQueueTimeoutUrl(), "Queue timeout url missing");
    Preconditions.checkEmptyString(
        conf.getPayPromotionQueueTimeoutUrl(), "Queue timeout url missing");
    Preconditions.checkNotNull(conf.getPayPromotionResultUrl(), "Result url missing");
    Preconditions.checkEmptyString(conf.getPayPromotionResultUrl(), "Result url missing");

    return Try.of(
            () ->
                createPayPromotionRequest(
                    conf.getInitiatorShortCode(),
                    conf.getInitiatorName(),
                    conf.getInitiatorSecurityCredential(),
                    destination,
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
      String destination, BigDecimal payableAmount, String description, String occasion) {
    checkInitiatorPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential());

    Preconditions.checkNotNull(conf.getPaySalaryQueueTimeoutUrl(), "Queue timeout url missing");
    Preconditions.checkEmptyString(conf.getPaySalaryQueueTimeoutUrl(), "Queue timeout url missing");
    Preconditions.checkNotNull(conf.getPaySalaryResultUrl(), "Result url missing");
    Preconditions.checkEmptyString(conf.getPaySalaryResultUrl(), "Result url missing");

    return Try.of(
            () ->
                createPaySalaryRequest(
                    conf.getInitiatorShortCode(),
                    conf.getInitiatorName(),
                    conf.getInitiatorSecurityCredential(),
                    destination,
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
      String transactionID, String description, String occasion) {
    checkTransactionQueryPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential(),
        conf.getTransactionQueryQueueTimeoutUrl(),
        conf.getTransactionQueryResultUrl());

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
      String customerPhoneNumber, String transactionID, String description, String occasion) {
    checkTransactionQueryPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential(),
        conf.getTransactionQueryQueueTimeoutUrl(),
        conf.getTransactionQueryResultUrl());

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
      DefaultAction defaultAction, String validationUrl, String confirmationUrl) {
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
      String transactionId, BigDecimal amount, String description, String occasion) {
    checkReverseTransactionPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential(),
        conf.getTransactionReversalQueueTimeoutUrl(),
        conf.getTransactionReversalResultUrl());

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
      String transactionId,
      BigDecimal amount,
      String description,
      String occasion) {
    checkReverseTransactionPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential(),
        conf.getTransactionReversalQueueTimeoutUrl(),
        conf.getTransactionReversalResultUrl());

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
  public AccountBalanceResponse queryBalance(String description) {
    checkInitiatorPreconditions(
        conf.getInitiatorName(),
        conf.getInitiatorShortCode(),
        conf.getInitiatorSecurityCredential());

    Preconditions.checkNotNull(
        conf.getAccountBalanceQueueTimeoutUrl(), "Account balance queue timeout url missing");
    Preconditions.checkEmptyString(
        conf.getAccountBalanceQueueTimeoutUrl(), "Account balance queue timeout url missing");
    Preconditions.checkNotNull(
        conf.getAccountBalanceResultUrl(), "Account balance result url missing");
    Preconditions.checkEmptyString(
        conf.getAccountBalanceResultUrl(), "Account balance result url missing");

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
      String description,
      String queueTimeoutUrl,
      String resultUrl) {
    Preconditions.checkNotNull(description, "Description missing");
    Preconditions.checkEmptyString(description, "Description missing");

    return AccountBalanceRequest.builder()
        .initiatorName(initiatorName)
        .businessShortCode(initiatorShortCode)
        .credentials(initiatorSecurityCredential)
        .transactionType(CommandID.ACCOUNT_BALANCE.getCommandId())
        .identifierType(IdentifierType.SHORTCODE.getIdentifierType())
        .description(description)
        .queueTimeoutUrl(queueTimeoutUrl)
        .resultUrl(resultUrl)
        .build();
  }

  private static CallbackUrlRegistrationRequest createCallbackUrlRegistrationRequest(
      String shortCode, DefaultAction defaultAction, String validationUrl, String confirmationUrl) {

    Preconditions.checkNotNull(defaultAction, "Default action missing");
    Preconditions.checkNotNull(validationUrl, "Validation Url missing");
    Preconditions.checkEmptyString(validationUrl, "Validation Url missing");
    Preconditions.checkNotNull(confirmationUrl, "Confirmation Url missing");
    Preconditions.checkEmptyString(confirmationUrl, "Confirmation Url missing");

    return CallbackUrlRegistrationRequest.builder()
        .shortCode(shortCode)
        .callbackType(defaultAction.getDefaultAction())
        .validationUrl(validationUrl)
        .confirmationUrl(confirmationUrl)
        .build();
  }

  private static InstantPaymentQueryRequest createPaymentQueryRequest(
      String shortCode, String passkey, String paymentId) {
    Preconditions.checkNotNull(paymentId, "PaymentID missing");
    Preconditions.checkEmptyString(paymentId, "PaymentID missing");

    String timestamp = GenerationUtils.generateTimestamp();

    return InstantPaymentQueryRequest.builder()
        .businessShortCode(shortCode)
        .password(GenerationUtils.generatePassword(shortCode, passkey, timestamp))
        .timestamp(timestamp)
        .paymentId(paymentId)
        .build();
  }

  private static InstantPaymentRequest createInstantPaymentRequest(
      String shortCode,
      String passkey,
      String customerPhoneNumber,
      BigDecimal payableAmount,
      String accountReference,
      String description,
      String callbackUrl) {
    Preconditions.checkNotNull(customerPhoneNumber, "Customer phone number missing");
    Preconditions.checkEmptyString(customerPhoneNumber, "Customer phone number missing");
    Preconditions.checkNotNull(accountReference, "Account reference missing");
    Preconditions.checkEmptyString(accountReference, "Account reference missing");
    Preconditions.checkNotNull(description, "Description missing");
    Preconditions.checkEmptyString(description, "Description missing");
    Preconditions.checkNotNull(payableAmount, "Payable amount missing");
    Preconditions.checkPositiveNumber(payableAmount, "Invalid payable amount");

    String timestamp = GenerationUtils.generateTimestamp();

    return InstantPaymentRequest.builder()
        .businessShortCode(shortCode)
        .password(GenerationUtils.generatePassword(shortCode, passkey, timestamp))
        .timestamp(timestamp)
        .transactionType(CommandID.CUSTOMER_PAY_BILL_ONLINE.getCommandId())
        .amount(payableAmount.toString())
        .customerPhoneNumber(customerPhoneNumber)
        .initiatorShortCode(shortCode)
        .phoneNumber(customerPhoneNumber)
        .callbackUrl(callbackUrl)
        .accountReference(accountReference)
        .transactionDescription(description)
        .build();
  }

  private static PaymentRequest createPayBusinessRequest(
      String initiatorShortCode,
      String initiatorName,
      String initiatorSecurityCredential,
      String destination,
      BigDecimal payableAmount,
      String description,
      String occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    return createPaymentRequest(
        CommandID.BUSINESS_PAYMENT,
        initiatorShortCode,
        initiatorName,
        initiatorSecurityCredential,
        destination,
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
      String destination,
      BigDecimal payableAmount,
      String description,
      String occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    return createPaymentRequest(
        CommandID.PROMOTION_PAYMENT,
        initiatorShortCode,
        initiatorName,
        initiatorSecurityCredential,
        destination,
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
      String destination,
      BigDecimal payableAmount,
      String description,
      String occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    return createPaymentRequest(
        CommandID.SALARY_PAYMENT,
        initiatorShortCode,
        initiatorName,
        initiatorSecurityCredential,
        destination,
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
      String destination,
      BigDecimal amount,
      String description,
      String occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    Preconditions.checkNotNull(destination, "Customer phone number missing");
    Preconditions.checkEmptyString(destination, "Customer phone number missing");
    Preconditions.checkNotNull(description, "Description missing");
    Preconditions.checkEmptyString(description, "Description missing");
    Preconditions.checkNotNull(amount, "Payable amount missing");
    Preconditions.checkPositiveNumber(amount, "Invalid payable amount");

    if (occasion == null) occasion = "";

    return PaymentRequest.builder()
        .initiatorName(initiatorName)
        .credentials(initiatorSecurityCredential)
        .transactionType(transactionType.getCommandId())
        .amount(amount.toString())
        .initiatorShortCode(initiatorShortCode)
        .destination(destination)
        .description(description)
        .queueTimeoutUrl(queueTimeoutUrl)
        .resultUrl(resultUrl)
        .occasion(occasion)
        .build();
  }

  private static TransactionQueryRequest createBusinessTransactionQueryRequest(
      String initiatorName,
      String initiatorShortCode,
      String initiatorSecurityCredential,
      String transactionID,
      String description,
      String occasion,
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
      String transactionID,
      String description,
      String occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    Preconditions.checkNotNull(customerPhoneNumber, "Customer phone number missing");
    Preconditions.checkEmptyString(customerPhoneNumber, "Customer phone number missing");

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
      String transactionID,
      String description,
      String occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    Preconditions.checkNotNull(transactionID, "TransactionID missing");
    Preconditions.checkEmptyString(transactionID, "TransactionID missing");
    Preconditions.checkNotNull(description, "Description missing");
    Preconditions.checkEmptyString(description, "Description missing");

    if (occasion == null) {
      occasion = "";
    }

    return TransactionQueryRequest.builder()
        .initiatorName(initiatorName)
        .credentials(initiatorSecurityCredential)
        .transactionType(CommandID.TRANSACTION_STATUS_QUERY.getCommandId())
        .identifierType(identifierType.getIdentifierType())
        .transactionID(transactionID)
        .sender(sender)
        .description(description)
        .occasion(occasion)
        .queueTimeoutUrl(queueTimeoutUrl)
        .resultUrl(resultUrl)
        .build();
  }

  private static TransactionReversalRequest createBusinessTransactionReversalRequest(
      String initiatorName,
      String initiatorShortCode,
      String initiatorSecurityCredential,
      String transactionId,
      BigDecimal amount,
      String description,
      String occasion,
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
      String transactionId,
      BigDecimal amount,
      String description,
      String occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    Preconditions.checkNotNull(customerPhoneNumber, "Customer phone number missing");
    Preconditions.checkEmptyString(customerPhoneNumber, "Customer phone number missing");

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
      String transactionID,
      BigDecimal amount,
      String description,
      String occasion,
      String queueTimeoutUrl,
      String resultUrl) {
    Preconditions.checkNotNull(transactionID, "TransactionID missing");
    Preconditions.checkEmptyString(transactionID, "TransactionID missing");
    Preconditions.checkNotNull(description, "Description missing");
    Preconditions.checkEmptyString(description, "Description missing");
    Preconditions.checkNotNull(amount, "Amount to reverse missing");
    Preconditions.checkPositiveNumber(amount, "Invalid reversible amount");

    if (occasion == null) occasion = "";

    return TransactionReversalRequest.builder()
        .initiatorName(initiatorName)
        .receiverParty(receiver)
        .receiverIdentifierType(identifierType.getIdentifierType())
        .credentials(initiatorSecurityCredential)
        .transactionType(CommandID.TRANSACTION_REVERSAL.getCommandId())
        .transactionID(transactionID)
        .amount(amount.toString())
        .description(description)
        .occasion(occasion)
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
    Preconditions.checkNotNull(name, "Initiator name missing");
    Preconditions.checkEmptyString(name, "Initiator name missing");
    Preconditions.checkNotNull(shortCode, "Initiator code missing");
    Preconditions.checkEmptyString(shortCode, "Initiator code missing");
    Preconditions.checkNotNull(securityCredential, "Initiator security credential missing");
    Preconditions.checkEmptyString(securityCredential, "Initiator security credential missing");
  }

  private static void checkInstantPayPreconditions(String shortCode, String passkey) {
    Preconditions.checkNotNull(shortCode, "LipaNaMpesa shortcode missing");
    Preconditions.checkEmptyString(shortCode, "LipaNaMpesa shortcode missing");
    Preconditions.checkNotNull(passkey, "LipaNaMpesa passkey missing");
    Preconditions.checkEmptyString(passkey, "LipaNaMpesa passkey missing");
  }

  private static void checkTransactionQueryPreconditions(
      String initiatorName,
      String initiatorShortCode,
      String securityCredential,
      String queueTimeoutUrl,
      String resultUrl) {
    checkInitiatorPreconditions(initiatorName, initiatorShortCode, securityCredential);

    Preconditions.checkNotNull(queueTimeoutUrl, "PaymentRequest query queue timeout url missing");
    Preconditions.checkEmptyString(
        queueTimeoutUrl, "PaymentRequest query queue timeout url missing");
    Preconditions.checkNotNull(resultUrl, "PaymentRequest query result url missing");
    Preconditions.checkEmptyString(resultUrl, "PaymentRequest query result url missing");
  }

  private static void checkReverseTransactionPreconditions(
      String initiatorName,
      String initiatorShortCode,
      String securityCredential,
      String queueTimeoutUrl,
      String resultUrl) {
    checkInitiatorPreconditions(initiatorName, initiatorShortCode, securityCredential);

    Preconditions.checkNotNull(queueTimeoutUrl, "Transaction reversal queue timeout url missing");
    Preconditions.checkEmptyString(
        queueTimeoutUrl, "Transaction reversal queue timeout url missing");
    Preconditions.checkNotNull(resultUrl, "Transaction reversal result url missing");
    Preconditions.checkEmptyString(resultUrl, "Transaction reversal result url missing");
  }
}
