package com.example.coe.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public ResponseEntity<String> getAllUsers(){
        return ResponseEntity.ok("hello");
    }
    @GetMapping(value="/{userId}")
    public ResponseEntity<String> getUser(@PathVariable String userId){
        return ResponseEntity.ok("Hello Erin" + userId);
    }
    @PostMapping
    public ResponseEntity<String> createUser(){
        return ResponseEntity.ok("User created");
    }
    @PutMapping(value = "/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId) {
        return ResponseEntity.ok("User updated: " + userId);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return ResponseEntity.ok("User deleted: " + userId);
    }

    @GetMapping(value = "/{userId}/todos")
    public ResponseEntity<String> getAllListsForUser(@PathVariable String userId) {
        return ResponseEntity.ok("Todos for user id: " + userId);
    }
}

