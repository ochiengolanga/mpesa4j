package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are successful but with a
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.DEBIT_ACCOUNT_INVALID
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.CREDIT_ACCOUNT_INVALID
 * response code.<br>
 */
public class InvalidAccountException extends MpesaApiException {
    public InvalidAccountException(String message) {
        super(message);
    }

    public InvalidAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
