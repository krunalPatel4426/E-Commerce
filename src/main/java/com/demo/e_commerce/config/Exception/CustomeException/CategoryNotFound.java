package com.demo.e_commerce.config.Exception.CustomeException;

public class CategoryNotFound extends RuntimeException{
    public CategoryNotFound(String message) {
        super(message);
    }
}
