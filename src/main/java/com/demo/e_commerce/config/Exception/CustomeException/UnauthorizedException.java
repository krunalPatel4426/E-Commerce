package com.demo.e_commerce.config.Exception.CustomeException;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
