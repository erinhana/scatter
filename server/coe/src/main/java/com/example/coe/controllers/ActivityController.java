package com.example.coe.controllers;

import com.example.coe.models.activities.ActivityDetailViewModel;
import com.example.coe.models.activities.ActivityViewModel;
import com.example.coe.models.activities.CreateActivityViewModel;
import com.example.coe.models.activities.UpdateActivityViewModel;
import com.example.coe.models.todos.TodoViewModel;
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
@RequestMapping("/activities")
@Tag(name = "Activities")
public class ActivityController {
    @GetMapping
    @Operation(summary = "Get All Activities")
    public ResponseEntity<List<ActivityViewModel>> getAllActivities() {

        var activities= List.of(new ActivityViewModel(1, 1, "test activity", "test activity description"));
        return null;
    }

    @GetMapping(value = "/{activityId}")
    @Operation(summary = "Get Activity")
    public ResponseEntity<ActivityDetailViewModel> getActivity(@PathVariable int activityId) {
        return ResponseEntity.ok(new ActivityDetailViewModel(activityId, 1, "Clean room", "Activity description", LocalDateTime.now(), LocalDateTime.now(), 15));
    }

    @GetMapping(value = "/types")
    @Operation(summary = "Get Activity Type")
    public ResponseEntity<String> getActivityType() {
        return ResponseEntity.ok("Activities" );
    }

    @PostMapping
    @Operation(summary = "Create Activity")
    public ResponseEntity<ActivityViewModel> createActivity(@RequestBody @Valid CreateActivityViewModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ActivityViewModel(1, 1, model.getTitle(), model.getDescription()));

    }

    @PutMapping(value = "/{activityId}")
    @Operation(summary = "Update Activity")
    public ResponseEntity<ActivityViewModel> updateActivity(@PathVariable @Valid UpdateActivityViewModel model) {
        return ResponseEntity.ok(new ActivityViewModel(3, 3, model.getTitle(), model.getDescription()));
    }

    @DeleteMapping(value = "/{activityId}")
    @Operation(summary = "Delete Activity")
    public ResponseEntity<Void> deleteActivity(@PathVariable int activityId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

