package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are successful but with a
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.LESS_THAN_MINIMUM_TRANSACTION_VALUE
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.MORE_THAN_MAXIMUM_TRANSACTION_VALUE
 * response code.<br>
 */
public class InvalildTransactionValueException extends MpesaApiException {
    public InvalildTransactionValueException(String message) {
        super(message);
    }

    public InvalildTransactionValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
