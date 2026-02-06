package com.demo.e_commerce.config.Exception.CustomeException;

public class DBError extends RuntimeException{
    public DBError(String message) {
        super(message);
    }
}
