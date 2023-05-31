package com.example.coe.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/activities")
public class ActivitiesController {
    @GetMapping
    public ResponseEntity<String> getAllActivities(){
        return ResponseEntity.ok("Activities");
    }
    @GetMapping(value="/{activityId}")
    public ResponseEntity<String> getActivity(@PathVariable String activityId){
        return ResponseEntity.ok("Activities" + activityIdId);
    }
    @PostMapping
    public ResponseEntity<String> createActivities(){
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

