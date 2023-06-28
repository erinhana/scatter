package com.example.coe.controllers;

import com.example.coe.models.blockers.BlockerViewModel;
import com.example.coe.models.blockers.CreateBlockerViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockers")
@Tag(name = "Blockers")
public class BlockerController {
    @GetMapping
    @Operation(summary = "Get All Blockers")
    public ResponseEntity<String> getAllBlockers() {
        return ResponseEntity.ok("Blockers");
    }

    @GetMapping(value = "/{blockerId}")
    @Operation(summary = "Get Blocker")
    public ResponseEntity<String> getBlocker(@PathVariable String blockerId) {
        return ResponseEntity.ok("Blockers" + blockerId);
    }

    @PostMapping
    @Operation(summary = "Create Blocker")
    public ResponseEntity<BlockerViewModel> createBlocker(@RequestBody @Valid CreateBlockerViewModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new BlockerViewModel(1, 1, model.getTitle(), model.getDescription(), 1));
    }

    @PutMapping(value = "/{blockerId}")
    @Operation(summary = "Update Blocker")
    public ResponseEntity<String> updateBlocker(@PathVariable String blockerId) {
        return ResponseEntity.ok("Blocker updated: " + blockerId);
    }

    @DeleteMapping(value = "/{blockerId}")
    @Operation(summary = "Delete Blocker")
    public ResponseEntity<String> deleteBlocker(@PathVariable String blockerId) {
        return ResponseEntity.ok("Blocker deleted: " + blockerId);
    }
}
