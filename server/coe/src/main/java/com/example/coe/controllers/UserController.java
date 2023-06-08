package com.example.coe.controllers;

import com.example.coe.models.users.CreateUserViewModel;
import com.example.coe.models.users.UserDetailsViewModel;
import com.example.coe.models.users.UserViewModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public ResponseEntity<List<UserViewModel>> getAllUsers() {
        return ResponseEntity.ok(List.of(
                new UserViewModel(1, "testemail@test.com", "Test", "User"),
                new UserViewModel(2, "testemail2@test.com", "Test2", "User2"),
                new UserViewModel(3, "testemail3@test.com", "Test3", "User3")));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDetailsViewModel> getUser(@PathVariable int userId) {
        return ResponseEntity.ok(
                new UserDetailsViewModel(
                        userId,
                        "testemail@test.com",
                        "Test",
                        "User",
                        10,
                        5,
                        2,
                        3));
    }

    @PostMapping
    public ResponseEntity<UserDetailsViewModel> createUser(@RequestBody @Valid CreateUserViewModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDetailsViewModel(
                1,
                model.getEmail(),
                model.getFirstName(),
                model.getLastName(),
                0,
                0,
                0,
                0));
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

