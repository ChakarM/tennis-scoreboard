package com.mddvc.spring.app.exception;

public class AppServiceException extends RuntimeException {
    public AppServiceException(String message) {
        super(message);
    }
}
