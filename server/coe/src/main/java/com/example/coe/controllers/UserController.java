package com.example.coe.controllers;

import com.example.coe.entities.User;
import com.example.coe.exception.NotFoundException;
import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.models.users.CreateUserViewModel;
import com.example.coe.models.users.UpdateUserViewModel;
import com.example.coe.models.users.UserDetailViewModel;
import com.example.coe.models.users.UserViewModel;
import com.example.coe.repositories.TodoRepository;
import com.example.coe.repositories.UserRepository;
import com.example.coe.utils.mapper.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {


    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final Mapper mapper;

    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @Operation(summary = "Get All Users")
    public ResponseEntity<List<UserViewModel>> getAllUsers() {


        var users = userRepository.findAll();


        return ResponseEntity.ok(mapper.map(users, UserViewModel.class));

    }


    @GetMapping(value = "/{userId}")
    @Operation(summary = "Get User")
    public ResponseEntity<UserDetailViewModel> getUser(@PathVariable int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user exists with Id:", userId));

        return ResponseEntity.ok(mapper.map(user, UserDetailViewModel.class));


    }


    @PostMapping
    @Operation(summary = "Create User")
    public ResponseEntity<UserViewModel> createUser(@RequestBody @Valid CreateUserViewModel model) {
        var newUser = mapper.map(model, User.class);
        newUser.setPassword(passwordEncoder.encode(model.getPassword()));
        var createdUser = userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(createdUser, UserViewModel.class));
    }


    @PutMapping(value = "/{userId}")
    @Operation(summary = "Update User")
    public ResponseEntity<Void> updateUser(@PathVariable Integer userId, @RequestBody @Valid UpdateUserViewModel model) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user exists with Id:", userId));

        mapper.map(model, user);
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Delete User")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        var existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user exists with Id:", userId));

        userRepository.delete(existingUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping(value = "/{userId}/todos")
    @Operation(summary = "Get All Todos For a User")
    public ResponseEntity<List<TodoViewModel>> getAllTodosForUser(@PathVariable int userId) {
        var todos = todoRepository.findByUserId(userId);


        return ResponseEntity.ok(mapper.map(todos, TodoViewModel.class));
    }
}



