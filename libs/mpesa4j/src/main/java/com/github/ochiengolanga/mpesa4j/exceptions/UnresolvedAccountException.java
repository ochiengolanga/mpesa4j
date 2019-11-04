package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are successful but with a
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.UNRESOLVED_DEBIT_ACCOUNT
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.UNRESOLVED_CREDIT_ACCOUNT
 * response code.<br>
 */
public class UnresolvedAccountException extends MpesaApiException {
    public UnresolvedAccountException(String message) {
        super(message);
    }

    public UnresolvedAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
