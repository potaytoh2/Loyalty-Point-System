package com.admin.controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {
    @GetMapping
    public String healthCheck(@RequestHeader Map<String, String> headers) {
        return "Admin service is up and running";
    }
}