package com.example.coe.controllers;

import com.example.coe.entities.Blocker;
import com.example.coe.exception.NotFoundException;
import com.example.coe.models.blockers.*;
import com.example.coe.repositories.BlockerRepository;
import com.example.coe.repositories.BlockerTypeRepository;
import com.example.coe.repositories.UserRepository;
import com.example.coe.security.AppUser;
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

@RestController
@RequestMapping("/blockers")
@Tag(name = "Blockers")
@RequiredArgsConstructor
public class BlockerController {

    private final BlockerRepository blockerRepository;
    private final BlockerTypeRepository blockerTypeRepository;
    private final Mapper mapper;
    private final UserRepository userRepository;
    private final AppUser.CurrentAppUser currentAppUser;


    @GetMapping
    @Operation(summary = "Get All Blockers")
    public ResponseEntity<List<BlockerViewModel>> getAllBlockers() {


        var blockers = blockerRepository.findAll();


        return ResponseEntity.ok(mapper.map(blockers, BlockerViewModel.class));
    }


    @GetMapping(value = "/{blockerId}")
    @Operation(summary = "Get Blocker")
    public ResponseEntity<BlockerDetailViewModel> getBlocker(@PathVariable int blockerId) throws Exception {
        var blocker = blockerRepository.findById(blockerId).orElseThrow(() -> new NotFoundException("Blocker not found"));


        return ResponseEntity.ok(mapper.map(blocker, BlockerDetailViewModel.class));


    }


    @GetMapping(value = "/types")
    @Operation(summary = "Get Blocker Types")
    public ResponseEntity<List<BlockerTypeViewModel>> getBlockerTypes() {
        var blockerTypes = blockerTypeRepository.findAll();
        return ResponseEntity.ok(mapper.map(blockerTypes, BlockerTypeViewModel.class));
    }


    @PostMapping
    @Operation(summary = "Create Blocker")
    public ResponseEntity<BlockerViewModel> createBlocker(@RequestBody @Valid CreateBlockerViewModel model) {

        var newBlocker = mapper.map(model, Blocker.class);
        var user = userRepository.getReferenceById(currentAppUser.getId());
        LocalDateTime date = LocalDateTime.now();
        newBlocker.setCreatedAt(date);
        newBlocker.setUpdatedAt(date);
        newBlocker.setUser(user);

        var createdBlocker = blockerRepository.save(newBlocker);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(createdBlocker, BlockerViewModel.class));
    }


    @PutMapping(value = "/{blockerId}")
    @Operation(summary = "Update Blocker")
    public ResponseEntity<BlockerViewModel> updateBlocker(@PathVariable int blockerId, @RequestBody @Valid UpdateBlockerViewModel model) {

        var existingBlocker = blockerRepository.findById(blockerId).orElseThrow(() -> new NotFoundException("Blocker not found"));

        mapper.map(model, existingBlocker);
        blockerRepository.save(existingBlocker);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @DeleteMapping(value = "/{blockerId}")
    @Operation(summary = "Delete Blocker")
    public ResponseEntity<Void> deleteBlocker(@PathVariable int blockerId) {
        var existingBlocker = blockerRepository.findById(blockerId)
                .orElseThrow(() -> new NotFoundException("No blocker exists with Id", blockerId));

        blockerRepository.delete(existingBlocker);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
