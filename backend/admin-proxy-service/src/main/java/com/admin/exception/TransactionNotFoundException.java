package com.admin.exception;

public class TransactionNotFoundException extends WebException {
    public TransactionNotFoundException(String msg) {
        super("Transaction with ID " + msg + " not found.");
    }
}
