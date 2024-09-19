package com.bank.service.exception.custom;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String errorWhileSendingEmail, Throwable cause) {
        super(errorWhileSendingEmail, cause);
    }
}
