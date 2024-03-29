package com.example.coe.controllers;

import com.example.coe.entities.Todo;
import com.example.coe.exception.NotFoundException;
import com.example.coe.models.todos.CreateTodoViewModel;
import com.example.coe.models.todos.TodoDetailViewModel;
import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.models.todos.UpdateTodoViewModel;
import com.example.coe.repositories.TodoRepository;
import com.example.coe.repositories.UserRepository;
import com.example.coe.utils.mapper.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        var todos = userId.isPresent() ? todoRepository.findByUserId(userId.get()) : todoRepository.findAll();

        return ResponseEntity.ok(mapper.map(todos, TodoViewModel.class));

    }


    @GetMapping(value = "/{todoId}")
    @Operation(summary = "Get Todo")
    public ResponseEntity<TodoDetailViewModel> getTodo(@PathVariable int todoId) throws Exception {
        var todo = todoRepository.findById(todoId).orElseThrow(() -> new NotFoundException("Todo not found"));


        return ResponseEntity.ok(mapper.map(todo, TodoDetailViewModel.class));

    }

    @PostMapping
    @Operation(summary = "Create Todo")
    public ResponseEntity<TodoViewModel> createTodo(@RequestBody @Valid CreateTodoViewModel model) {

        var user = userRepository.findById(model.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));

        var newTodo = mapper.map(model, Todo.class);

        newTodo.setUser(user);
        newTodo.setCreatedAt(LocalDateTime.now());
        newTodo.setUpdatedAt(LocalDateTime.now());

        var createdTodo = todoRepository.save(newTodo);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(createdTodo, TodoViewModel.class));
    }


    @PutMapping(value = "/{todoId}")
    @Operation(summary = "Update Todo")
    public ResponseEntity<TodoViewModel> updateTodo(@PathVariable int todoId, @RequestBody @Valid UpdateTodoViewModel model) {

        var existingTodo = todoRepository.findById(todoId).orElseThrow(() -> new NotFoundException("Todo not found"));

        mapper.map(model, existingTodo);
        todoRepository.save(existingTodo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{todoId}")
    @Operation(summary = "Delete Todo")
    public ResponseEntity<Void> deleteTodo(@PathVariable int todoId) {
        var existingTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException("No todo exists with Id", todoId));

        todoRepository.delete(existingTodo);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
