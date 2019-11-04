package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are successful but with a
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.INSUFFICIENT_FUNDS response code.<br>
 */
public class InsufficientFundsException extends MpesaApiException {
    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
