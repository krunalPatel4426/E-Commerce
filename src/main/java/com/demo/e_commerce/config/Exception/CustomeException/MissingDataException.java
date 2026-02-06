package com.demo.e_commerce.config.Exception.CustomeException;

public class MissingDataException extends RuntimeException{
    public MissingDataException(String message) {
        super(message);
    }
}
