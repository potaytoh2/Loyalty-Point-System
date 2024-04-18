package com.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user.exception.WebException;

@ControllerAdvice
public class ExceptionCatcher {
    @ExceptionHandler(WebException.class)
    public ResponseEntity<String> notFound(WebException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
