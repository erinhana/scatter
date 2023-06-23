package com.example.coe.controllers;

import com.example.coe.models.activities.ActivityViewModel;
import com.example.coe.models.todos.TodoViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
@Tag(name = "Activities")
public class ActivityController {
    @GetMapping
    @Operation(summary = "Get All Activities")
    public ResponseEntity<List<ActivityViewModel>> getAllActivities() {

        var todos= List.of(new ActivityViewModel(1, 1, "test activity", "test activity description"));
        return null;
    }

    @GetMapping(value = "/{activityId}")
    public ResponseEntity<String> getActivity(@PathVariable String activityId) {
        return ResponseEntity.ok("Activities" + activityId);
    }

    @GetMapping(value = "/{types}")
    public ResponseEntity<String> getActivityType(@PathVariable String activityId) {
        return ResponseEntity.ok("Activities" + activityId);
    }

    @PostMapping
    public ResponseEntity<String> createActivities() {
        return ResponseEntity.ok("Activity created");
    }

    @PutMapping(value = "/{activityId}")
    public ResponseEntity<String> updateActivity(@PathVariable String activityId) {
        return ResponseEntity.ok("Activity updated: " + activityId);
    }

    @DeleteMapping(value = "/{activityId}")
    public ResponseEntity<String> deleteActivity(@PathVariable String activityId) {
        return ResponseEntity.ok("Activity deleted: " + activityId);
    }
}

