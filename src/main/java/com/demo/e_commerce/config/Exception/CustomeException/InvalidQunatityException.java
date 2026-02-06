package com.demo.e_commerce.config.Exception.CustomeException;

public class InvalidQunatityException extends RuntimeException {
    public InvalidQunatityException(String message){
        super(message);
    }
}
