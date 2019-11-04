package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are successful but with a
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.DUPLICATE_DETECTED
 * response code.<br>
 */
public class DuplicateDetectedException extends MpesaApiException {
    public DuplicateDetectedException(String message) {
        super(message);
    }

    public DuplicateDetectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
