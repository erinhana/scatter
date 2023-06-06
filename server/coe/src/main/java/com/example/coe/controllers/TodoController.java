package com.example.coe.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {
    @GetMapping
    public ResponseEntity<String> getAllTodos(){
        return ResponseEntity.ok("todos");
    }
    @GetMapping(value="/{todoId}")
    public ResponseEntity<String> getUser(@PathVariable String todoId){
        return ResponseEntity.ok("Todos" + todoId);
    }
    @PostMapping
    public ResponseEntity<String> createTodo(){
        return ResponseEntity.ok("Todo created");
    }
    @PutMapping(value = "/{todoId}")
    public ResponseEntity<String> updateTodo(@PathVariable String todoId) {
        return ResponseEntity.ok("Todo updated: " + todoId);
    }

    @DeleteMapping(value = "/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable String todoId) {
        return ResponseEntity.ok("Todo deleted: " + todoId);
    }
}
