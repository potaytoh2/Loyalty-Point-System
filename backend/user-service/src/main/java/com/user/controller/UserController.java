package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user.entity.User;
import com.user.service.UserService;

import jakarta.validation.Valid;

import com.user.payload.request.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService service;

    // health endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.status(HttpStatus.OK).body("User service is up and running");
    }

    @GetMapping("/all_users")
    public Iterable<User> getAllUsers() {
        return service.getUsers();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id) {
        return service.getUserById(id);
    }

    @PostMapping("/new_user")
    public User createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return service.createUser(createUserRequest);
    }

    @PutMapping("/info/{id}")
    public User updateUser(@PathVariable String id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return service.updateUser(updateUserRequest, id);
    }

    @DeleteMapping("/user/{id}")
    public User deleteUser(@PathVariable String id) {
        return service.deleteUserById(id);
    }

    @GetMapping("/permissions/{id}")
    public List<String> userPermissions(@PathVariable String id) {
        return service.getUserPerms(id);
    }
}