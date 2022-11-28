package com.brandlogs.storeservice.exception;

public class QuantityExceededException extends RuntimeException{
    public QuantityExceededException(String message) {
        super(message);
    }

    public QuantityExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
