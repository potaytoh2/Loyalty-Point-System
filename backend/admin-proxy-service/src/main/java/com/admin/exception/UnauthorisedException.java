package com.admin.exception;

public class UnauthorisedException extends WebException {
    public UnauthorisedException(String resource) {
        super("Access denied for resource: " + resource);
    }
}