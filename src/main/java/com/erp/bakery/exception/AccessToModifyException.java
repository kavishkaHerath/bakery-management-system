package com.erp.bakery.exception;

public class AccessToModifyException extends RuntimeException {
    public AccessToModifyException(String message) {
        super(message);
    }
}
