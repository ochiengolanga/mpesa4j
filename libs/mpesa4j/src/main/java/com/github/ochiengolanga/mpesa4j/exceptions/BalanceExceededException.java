package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are successful but with a
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.WOULD_EXCEED_MINIMUM_BALANCE
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.WOULD_EXCEED_MAXIMUM_BALANCE
 * response code.<br>
 */
public class BalanceExceededException extends MpesaApiException {
    public BalanceExceededException(String message) {
        super(message);
    }

    public BalanceExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
