package com.admin.controller;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.admin.entity.Log;
import com.admin.service.ProxyService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ProxyController {
    private final ProxyService proxy;

    @Autowired
    public ProxyController(ProxyService proxy) {
        this.proxy = proxy;
    }

    @GetMapping("/log")
    public List<Log> getLogs() {
        return proxy.getLogs();
    }

    @RequestMapping("/user/**")
    public ResponseEntity<String> forwardUser(HttpServletRequest request) throws IOException {
        return proxy.forwardUsers(request);
    }

    @RequestMapping("/points/**")
    public ResponseEntity<String> forwardPoints(HttpServletRequest request) throws IOException {
        return proxy.forwardPoints(request);
    }
}
