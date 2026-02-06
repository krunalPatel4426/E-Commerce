package com.demo.e_commerce.config.Exception.CustomeException;

public class NegativePriceException extends RuntimeException{
    public NegativePriceException(String message){
        super(message);
    }
}
