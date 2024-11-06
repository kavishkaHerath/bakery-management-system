package com.erp.bakery.exception;

public class DuplicateFieldException extends RuntimeException{
    public DuplicateFieldException(String message){
        super(message);
    }
}
