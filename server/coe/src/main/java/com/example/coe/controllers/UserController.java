package com.example.coe.controllers;

import com.example.coe.entities.User;
import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.models.users.CreateUserViewModel;
import com.example.coe.models.users.UpdateUserViewModel;
import com.example.coe.models.users.UserDetailViewModel;
import com.example.coe.models.users.UserViewModel;
import com.example.coe.repositories.UserRepository;
import com.example.coe.utils.mapper.Mapper;
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
    private final Mapper mapper;
    @GetMapping
    @Operation(summary = "Get All Users")
    public ResponseEntity<List<UserViewModel>> getAllUsers() {


        var users = userRepository.findAll();


        return ResponseEntity.ok( mapper.map(users, UserViewModel.class));

    }




    @GetMapping(value = "/{userId}")
    @Operation(summary = "Get User")
    public ResponseEntity<UserViewModel> getUser(@PathVariable int userId) {


        var user= userRepository.findById(userId);
        return user.map(value -> ResponseEntity.ok(new UserViewModel(
                value.getId(),
                value.getEmailAddress(),
                value.getFirstName(),
                value.getLastName())
        )).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Create User")
    public ResponseEntity<UserViewModel> createUser(@RequestBody @Valid CreateUserViewModel model) {
        var newUser = new User(0, model.getEmail(), model.getFirstName(), model.getLastName(), model.getPassword());
        var createdUser = userRepository.save(newUser);


        return ResponseEntity.status(HttpStatus.CREATED).body(new UserViewModel(
                createdUser.getId(),
                createdUser.getEmailAddress(),
                createdUser.getFirstName(),
                createdUser.getLastName()
                ));
    }


    @PutMapping(value = "/{userId}")
    @Operation(summary = "Update User")
    public ResponseEntity<Void> updateUser(@PathVariable Integer userId, @RequestBody @Valid UpdateUserViewModel model) {
        var user= userRepository.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        user.get().setEmailAddress(model.getEmail());
        user.get().setFirstName(model.getFirstName());
        user.get().setLastName(model.getLastName());

        userRepository.save(user.get());


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{userId}")
    @Operation(summary = "Delete User")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {

        var user= userRepository.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        userRepository.delete(user.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{userId}/todos")
    @Operation(summary = "Get All Todos For a User")
    public ResponseEntity<List<TodoViewModel>> getAllTodosForUser(@PathVariable String userId) {
        var todos= List.of(new TodoViewModel(1, 1, "test todo"));
        return ResponseEntity.ok(todos);
    }
}

