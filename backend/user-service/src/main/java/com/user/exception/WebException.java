package com.user.exception;

import org.springframework.http.HttpStatus;

public class WebException extends RuntimeException {
    protected HttpStatus status;

    public WebException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public WebException(String msg) {
        this(msg, HttpStatus.BAD_REQUEST);
    }
}
