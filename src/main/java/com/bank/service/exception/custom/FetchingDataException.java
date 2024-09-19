package com.bank.service.exception.custom;

public class FetchingDataException extends RuntimeException {
    public FetchingDataException(String message, Throwable cause) {
        super(message, cause);
    }
    public FetchingDataException(String message) {
        super(message);
    }

}