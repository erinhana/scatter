package com.example.coe.controllers;

import com.example.coe.models.users.CreateUserViewModel;
import com.example.coe.models.users.UpdateUserViewModel;
import com.example.coe.models.users.UserDetailViewModel;
import com.example.coe.models.users.UserViewModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<UserDetailViewModel> getUser(@PathVariable int userId) {
        return ResponseEntity.ok(
                new UserDetailViewModel(
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
    public ResponseEntity<UserDetailViewModel> createUser(@RequestBody @Valid CreateUserViewModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDetailViewModel(
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
    public ResponseEntity<UserViewModel> updateUser(@PathVariable @Valid UpdateUserViewModel model) {
        return ResponseEntity.ok(new UserViewModel(2, model.getEmail(), model.getFirstName(), model.getLastName()));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{userId}/todos")
    public ResponseEntity<String> getAllTodosForUser(@PathVariable String userId) {
        return ResponseEntity.ok("Todos for user id: " + userId);
    }
}

