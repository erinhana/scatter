package com.example.coe.controllers;

import com.example.coe.entities.Activity;
import com.example.coe.exception.NotFoundException;
import com.example.coe.models.activities.*;
import com.example.coe.models.activities.ActivityDetailViewModel;
import com.example.coe.models.activities.ActivityTypeViewModel;
import com.example.coe.models.activities.ActivityViewModel;
import com.example.coe.models.activities.CreateActivityViewModel;
import com.example.coe.models.activities.UpdateActivityViewModel;
import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.models.users.UpdateUserViewModel;
import com.example.coe.repositories.ActivityRepository;
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

@RestController
@RequestMapping("/activities")
@Tag(name = "Activities")
@RequiredArgsConstructor


public class ActivityController {

    private final ActivityRepository activityRepository;
    private final Mapper mapper;

    @GetMapping
    @Operation(summary = "Get All Activities")
    public ResponseEntity<List<ActivityViewModel>> getAllActivities() {

        var activities = activityRepository.findAll();
        return ResponseEntity.ok(mapper.map(activities, ActivityViewModel.class));
    }



    @GetMapping(value = "/{activityId}")
    @Operation(summary = "Get Activity")
    public ResponseEntity<ActivityDetailViewModel> getActivity(@PathVariable int activityId) {

        var activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException("No activity exists with this id"));

        return ResponseEntity.ok(mapper.map(activity, ActivityDetailViewModel.class));


    }




    @GetMapping(value = "/types")
    @Operation(summary = "Get Activity Type")
    public ResponseEntity<ActivityTypeViewModel> getActivityType() {
        return ResponseEntity.ok(new ActivityTypeViewModel());
    }




    @PostMapping
    @Operation(summary = "Create Activity")
    public ResponseEntity<ActivityViewModel> createActivity(@RequestBody @Valid CreateActivityViewModel model) {
        var newActivity = mapper.map(model, Activity.class);
        var createdActivity = activityRepository.save(newActivity);

       return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(createdActivity, ActivityViewModel.class));

    }





    @PutMapping(value = "/{activityId}")
    @Operation(summary = "Update Activity")
    public ResponseEntity<ActivityViewModel> updateActivity(@PathVariable int activityId, @RequestBody @Valid UpdateActivityViewModel model) {
        var activity = activityRepository.findById(activityId);
        if (activity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        mapper.map(model, activity.get());
        activityRepository.save(activity.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






    @DeleteMapping(value = "/{activityId}")
    @Operation(summary = "Delete Activity")
    public ResponseEntity<Void> deleteActivity(@PathVariable int activityId) {
        var existingActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new NotFoundException("No activity exists with this Id", activityId));

        activityRepository.delete(existingActivity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

