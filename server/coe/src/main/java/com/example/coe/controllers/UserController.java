package com.example.coe.controllers;

import com.example.coe.models.users.UserViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public ResponseEntity<List<UserViewModel>> getAllUsers(){
var user= new UserViewModel(1, "testemail@test.com", "Test", "User");
        return ResponseEntity.ok(List.of(user));
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

