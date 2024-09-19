package com.bank.service.exception.custom;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    public NotFoundException(String msg) {
        super(msg);
    }

}
