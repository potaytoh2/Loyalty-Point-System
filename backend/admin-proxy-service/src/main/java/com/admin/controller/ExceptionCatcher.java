package com.admin.controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.admin.exception.WebException;
import com.admin.utils.JSONBuilder;

@ControllerAdvice
public class ExceptionCatcher {
    @ExceptionHandler(WebException.class)
    public ResponseEntity<Map<String, Object>> webexcept(WebException e) {
        return ResponseEntity.badRequest().body(new JSONBuilder().addAttribute("reason", e.getMessage()).build());
    }
}
