package com.croft.mailerbackend.exceptions;

import com.croft.mailerbackend.ml.BARTService;

public class BARTException extends Exception {
    private String message;
    public BARTException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
