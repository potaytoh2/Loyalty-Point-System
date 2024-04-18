package com.points.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.points.Entity.Points;
import com.points.Service.PointsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PointsController {

    @Autowired
    private final PointsService service;

    @GetMapping
    public ResponseEntity<String> f() {
        return ResponseEntity.status(HttpStatus.OK).body("Response content here");
    }
    

    @GetMapping("/points")
    public ResponseEntity<Iterable<Points>> getAllPoints(@RequestParam String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getPointsByUserId(userId));
    }

    @GetMapping("/points/{id}")
    public ResponseEntity<Points> getPoints(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getPointsById(id));
    }

    @PostMapping(value = "/points/{id}")
    public ResponseEntity<Points> updatePoints(@PathVariable String id, @RequestBody Map<String, String> json) {
        int balance = Integer.parseInt(json.get("new_balance"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updatePointsById(id, balance));
    }
    
}