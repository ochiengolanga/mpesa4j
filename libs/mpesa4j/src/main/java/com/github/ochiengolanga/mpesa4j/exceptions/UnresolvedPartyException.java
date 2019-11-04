package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are successful but with a
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.UNRESOLVED_PRIMARY_PARTY
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.UNRESOLVED_RECEIVER_PARTY
 * response code.<br>
 */
public class UnresolvedPartyException extends MpesaApiException {
    public UnresolvedPartyException(String message) {
        super(message);
    }

    public UnresolvedPartyException(String message, Throwable cause) {
        super(message, cause);
    }
}
