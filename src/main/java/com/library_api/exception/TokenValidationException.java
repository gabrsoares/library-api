package com.library_api.exception;

public class TokenValidationException extends RuntimeException{
    public TokenValidationException(String message){
        super(message);
    }
}
