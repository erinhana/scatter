package com.example.coe.controllers;

import com.example.coe.models.todos.CreateTodoViewModel;
import com.example.coe.models.todos.TodoDetailViewModel;
import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.models.todos.UpdateTodoViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/todos")
@Tag(name = "Todos")
public class TodoController {
    @GetMapping
    @Operation(summary = "Get All Todos")
    public ResponseEntity<List<TodoViewModel>> getAllTodos() {

        var todos= List.of(new TodoViewModel(1, 1, "test todo"));
        return ResponseEntity.ok(todos);
    }

    @GetMapping(value = "/{todoId}")
    @Operation(summary = "Get Todo")
    public ResponseEntity<TodoDetailViewModel> getTodo(@PathVariable int todoId) {
        return ResponseEntity.ok(new TodoDetailViewModel(todoId, 1, "test todo", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()));
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
