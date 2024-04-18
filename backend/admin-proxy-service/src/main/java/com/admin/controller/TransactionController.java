package com.admin.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.admin.service.TransactionService;
import com.admin.utils.JSONBuilder;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/request/user/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createUserRequest(String map, @RequestHeader Map<String, String> headers, @PathVariable String id) {
        transactionService.createUserTransaction(map, headers, id);
        return accepted();
    }

    @PostMapping("/request/points/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createPointsRequest(String map, @RequestHeader Map<String, String> headers, @PathVariable String id) {
        transactionService.createPointsTransaction(map, headers, id);
        return accepted();
    }

    private Map<String, Object> accepted() {
        return new JSONBuilder().addAttribute("status", "Transaction created and pending approval").build();
    }

    @GetMapping("/accept/{id}")
    public Map<String, Object> acceptTransaction(@PathVariable String id) {
        transactionService.updateTransactionStatus(id, true);
        return new JSONBuilder().addAttribute("status", "Approved").build();
    }
}
