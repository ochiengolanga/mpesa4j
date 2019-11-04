package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are successful but with a
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.UNRESOLVED_INITIATOR
 * response code.<br>
 */
public class UnresolvedInitiatorException extends MpesaApiException {
    public UnresolvedInitiatorException(String message) {
        super(message);
    }

    public UnresolvedInitiatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
