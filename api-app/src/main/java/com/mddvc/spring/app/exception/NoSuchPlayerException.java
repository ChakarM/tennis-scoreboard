package com.mddvc.spring.app.exception;

public class NoSuchPlayerException extends AppServiceException {
    public NoSuchPlayerException(String message) {
        super(message);
    }
}
