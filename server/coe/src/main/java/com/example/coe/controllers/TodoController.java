package com.example.coe.controllers;

import com.example.coe.exception.NotFoundException;
import com.example.coe.models.todos.CreateTodoViewModel;
import com.example.coe.models.todos.TodoDetailViewModel;
import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.models.todos.UpdateTodoViewModel;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
@Tag(name = "Todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;
    @GetMapping
    @Operation(summary = "Get All Todos")
    public ResponseEntity<List<TodoViewModel>> getAllTodos(@RequestParam Optional<Integer> userId) {
        var todos = userId.isPresent() ? todoRepository.findByUserId(userId.get()): todoRepository.findAll();



        return ResponseEntity.ok( mapper.map(todos, TodoViewModel.class));

    }


    @GetMapping(value = "/{todoId}")
    @Operation(summary = "Get Todo")
    public ResponseEntity<TodoDetailViewModel> getTodo(@PathVariable int todoId) throws Exception {
        var todo = todoRepository.findById(todoId).orElseThrow(() -> new NotFoundException("Todo not found"));


        return ResponseEntity.ok( mapper.map(todo, TodoDetailViewModel.class));

    }

    @PostMapping
    @Operation(summary = "Create Todo")
    public ResponseEntity<TodoViewModel> createTodo(@RequestBody @Valid CreateTodoViewModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new TodoViewModel(1, 1, model.getDescription()));
    }



    @PutMapping(value = "/{todoId}")
    @Operation(summary = "Update Todo")
    public ResponseEntity<TodoViewModel> updateTodo(@PathVariable @Valid UpdateTodoViewModel model) {
        return ResponseEntity.ok(new TodoViewModel(1, 1, model.getDescription()));
    }

    @DeleteMapping(value = "/{todoId}")
    @Operation(summary = "Delete Todo")
    public ResponseEntity<Void> deleteTodo(@PathVariable int todoId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
