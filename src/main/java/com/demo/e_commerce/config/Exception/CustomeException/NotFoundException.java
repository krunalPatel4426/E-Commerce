package com.demo.e_commerce.config.Exception.CustomeException;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
