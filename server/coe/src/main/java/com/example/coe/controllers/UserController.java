package com.example.coe.controllers;

import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.models.users.CreateUserViewModel;
import com.example.coe.models.users.UpdateUserViewModel;
import com.example.coe.models.users.UserDetailViewModel;
import com.example.coe.models.users.UserViewModel;
import com.example.coe.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {


    private final UserRepository userRepository;
    @GetMapping
    @Operation(summary = "Get All Users")
    public ResponseEntity<List<UserViewModel>> getAllUsers() {


        var users = userRepository.findAll();
        return ResponseEntity.ok(users.stream().map(x -> new UserViewModel(x.getId(), x.getEmailAddress(),x.getFirstName(),x.getLastName())).collect(Collectors.toList()));

    }

    @GetMapping(value = "/{userId}")
    @Operation(summary = "Get User")
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
    @Operation(summary = "Create User")
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
    @Operation(summary = "Update User")
    public ResponseEntity<UserViewModel> updateUser(@PathVariable @Valid UpdateUserViewModel model) {
        return ResponseEntity.ok(new UserViewModel(2, model.getEmail(), model.getFirstName(), model.getLastName()));
    }

    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Delete User")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{userId}/todos")
    @Operation(summary = "Get All Todos For a User")
    public ResponseEntity<List<TodoViewModel>> getAllTodosForUser(@PathVariable String userId) {
        var todos= List.of(new TodoViewModel(1, 1, "test todo"));
        return ResponseEntity.ok(todos);
    }
}

