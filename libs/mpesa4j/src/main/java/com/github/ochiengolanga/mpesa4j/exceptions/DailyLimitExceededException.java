package com.github.ochiengolanga.mpesa4j.exceptions;

/**
 * An exception class that will be thrown when M-Pesa API calls are successful but with a
 * {@link com.github.ochiengolanga.mpesa4j.models.responses.MpesaResponseCode}.WOULD_EXCEED_DAILY_TRANSFER_LIMIT response code.<br>
 */
public class DailyLimitExceededException extends MpesaApiException {
    public DailyLimitExceededException(String message) {
        super(message);
    }

    public DailyLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
