package com.example.coe.controllers;

import com.example.coe.models.blockers.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/blockers")
@Tag(name = "Blockers")
public class BlockerController {
    @GetMapping
    @Operation(summary = "Get All Blockers")
    public ResponseEntity<List<BlockerViewModel>> getAllBlockers() {

        var blockers= List.of(new BlockerViewModel(1, 1, "test blocker", "test blocker description", 1));
        return null;
    }

    @GetMapping(value = "/{blockerId}")
    @Operation(summary = "Get Blocker")
    public ResponseEntity<BlockerDetailViewModel> getBlocker(@PathVariable int blockerId) {
        return ResponseEntity.ok(new BlockerDetailViewModel(blockerId, 1, "Hoover broken", "Unable to clean room as hoover is broken", LocalDateTime.now(), LocalDateTime.now(), 1));
    }

    @GetMapping(value = "/types")
    @Operation(summary = "Get Blocker Types")
    public ResponseEntity<BlockerTypeViewModel> getBlockerTypes() {
        return ResponseEntity.ok(new BlockerTypeViewModel());
    }

    @PostMapping
    @Operation(summary = "Create Blocker")
    public ResponseEntity<BlockerViewModel> createBlocker(@RequestBody @Valid CreateBlockerViewModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new BlockerViewModel(1, 1, model.getTitle(), model.getDescription, 1));
    }

    @PutMapping(value = "/{blockerId}")
    @Operation(summary = "Update Blocker")
    public ResponseEntity<BlockerViewModel> updateBlocker(@PathVariable @Valid UpdateBlockerViewModel model) {
        return ResponseEntity.ok(new BlockerViewModel(2, 2, model.getTitle(), model.getDescription(), 2));
    }

    @DeleteMapping(value = "/{blockerId}")
    @Operation(summary = "Delete Blocker")
    public ResponseEntity<Void> deleteBlocker(@PathVariable int blockerId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
