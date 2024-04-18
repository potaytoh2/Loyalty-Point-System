package com.user.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends WebException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}