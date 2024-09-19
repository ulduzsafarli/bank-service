package com.bank.service.exception.custom;

import java.io.Serial;

public class InvalidPinException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidPinException(String msg) {
        super(msg);
    }
}
