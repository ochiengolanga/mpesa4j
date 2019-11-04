package com.github.ochiengolanga.mpesa4j.models.responses;

import com.github.ochiengolanga.mpesa4j.exceptions.*;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

import static io.vavr.API.*;

/** A data interface representing an AccountBalanceResponse M-Pesa API response */
@Getter
@ToString
public final class MpesaResponse implements MpesaResponseCode, java.io.Serializable {
    private static final long serialVersionUID = 7092906238192790921L;

    @SerializedName("ConversationID")
    private String conversationId;

    @SerializedName(value = "OriginatorConversationID", alternate = "OriginatorCoversationID")
    private String originatorConversationId;

    @SerializedName("ResponseCode")
    private String responseCode;

    @SerializedName("ResponseDescription")
    private String responseDescription;

    @SerializedName("MerchantRequestID")
    private String merchantRequestId;

    @SerializedName("CheckoutRequestID")
    private String checkoutRequestId;

    @SerializedName("CustomerMessage")
    private String customerMessage;

    @SerializedName("ResultCode")
    private String resultCode;

    @SerializedName("ResultDesc")
    private String resultDescription;

    private MpesaResponse() throws MpesaApiException {
        Integer code = Integer.valueOf(this.responseCode);

        if (code != 0) {
            handleResponseError(code);
        }
    }

    private static void handleResponseError(int responseCode) throws MpesaApiException {
        MpesaApiException exception;

        exception = Match(responseCode).of(
                Case($(INSUFFICIENT_FUNDS), new InsufficientFundsException(getResponse(responseCode))),
                Case($(LESS_THAN_MINIMUM_TRANSACTION_VALUE), new InvalildTransactionValueException(getResponse(responseCode))),
                Case($(MORE_THAN_MAXIMUM_TRANSACTION_VALUE), new InvalildTransactionValueException(getResponse(responseCode))),
                Case($(WOULD_EXCEED_DAILY_TRANSFER_LIMIT), new DailyLimitExceededException(getResponse(responseCode))),
                Case($(WOULD_EXCEED_MINIMUM_BALANCE), new BalanceExceededException(getResponse(responseCode))),
                Case($(WOULD_EXCEED_MAXIMUM_BALANCE), new BalanceExceededException(getResponse(responseCode))),
                Case($(UNRESOLVED_PRIMARY_PARTY), new UnresolvedPartyException(getResponse(responseCode))),
                Case($(UNRESOLVED_RECEIVER_PARTY), new UnresolvedPartyException(getResponse(responseCode))),
                Case($(DEBIT_ACCOUNT_INVALID), new InvalidAccountException(getResponse(responseCode))),
                Case($(CREDIT_ACCOUNT_INVALID), new InvalidAccountException(getResponse(responseCode))),
                Case($(UNRESOLVED_DEBIT_ACCOUNT), new UnresolvedAccountException(getResponse(responseCode))),
                Case($(UNRESOLVED_CREDIT_ACCOUNT), new UnresolvedAccountException(getResponse(responseCode))),
                Case($(DUPLICATE_DETECTED), new DuplicateDetectedException(getResponse(responseCode))),
                Case($(INTERNAL_FAILURE), new MpesaApiException(getResponse(responseCode))),
                Case($(UNRESOLVED_INITIATOR), new UnresolvedInitiatorException(getResponse(responseCode))),
                Case($(TRAFFIC_BLOCKING_CONDITION_IN_PLACE), new MpesaApiException(getResponse(responseCode))),
                Case($(), new MpesaApiException(""))
        );

        throw exception;
    }

    public static String getResponse(int responseCode) {
        return Match(responseCode).of(
                Case($(SUCCESS), "Success"),
                Case($(INSUFFICIENT_FUNDS), "Insufficient Funds"),
                Case($(LESS_THAN_MINIMUM_TRANSACTION_VALUE), "Less Than Minimum Transaction Value"),
                Case($(MORE_THAN_MAXIMUM_TRANSACTION_VALUE), "More Than Maximum Transaction Value"),
                Case($(WOULD_EXCEED_DAILY_TRANSFER_LIMIT), "Would Exceed Daily Transfer Limit"),
                Case($(WOULD_EXCEED_MINIMUM_BALANCE), "Would Exceed Minimum Balance"),
                Case($(WOULD_EXCEED_MAXIMUM_BALANCE), "Would Exceed Maximum Balance"),
                Case($(UNRESOLVED_PRIMARY_PARTY), "Unresolved Primary Party"),
                Case($(UNRESOLVED_RECEIVER_PARTY), "Unresolved Receiver Party"),
                Case($(DEBIT_ACCOUNT_INVALID), "Invalid Debit Account"),
                Case($(CREDIT_ACCOUNT_INVALID), "Invalid Credit Account"),
                Case($(UNRESOLVED_DEBIT_ACCOUNT), "Unresolved Debit Account"),
                Case($(UNRESOLVED_CREDIT_ACCOUNT), "Unresolved Credit Account"),
                Case($(DUPLICATE_DETECTED), "Duplicate Detected"),
                Case($(INTERNAL_FAILURE), "Internal Failure"),
                Case($(UNRESOLVED_INITIATOR), "Unresolved Initiator"),
                Case($(TRAFFIC_BLOCKING_CONDITION_IN_PLACE), "Traffic blocking condition in place"),
                Case($(), "")
        );
    }
}
