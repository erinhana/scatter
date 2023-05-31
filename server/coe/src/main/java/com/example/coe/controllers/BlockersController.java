package com.example.coe.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blockers")
public class BlockersController {
    @GetMapping
    public ResponseEntity<String> getAllBlockers(){
        return ResponseEntity.ok("Blockers");
    }
    @GetMapping(value="/{blockerId}")
    public ResponseEntity<String> getBlocker(@PathVariable String blockerId){
        return ResponseEntity.ok("Blockers" + blockerId);
    }
    @PostMapping
    public ResponseEntity<String> createBlockers(){
        return ResponseEntity.ok("Blocker created");
    }
    @PutMapping(value = "/{blockerId}")
    public ResponseEntity<String> updateBlocker(@PathVariable String blockerId) {
        return ResponseEntity.ok("Blocker updated: " + blockerId);
    }

    @DeleteMapping(value = "/{blockerId}")
    public ResponseEntity<String> deleteBlocker(@PathVariable String blockerId) {
        return ResponseEntity.ok("Blocker deleted: " + blockerId);
    }
}
