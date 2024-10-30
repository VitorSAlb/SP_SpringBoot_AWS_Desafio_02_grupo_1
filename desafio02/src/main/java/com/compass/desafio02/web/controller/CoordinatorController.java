package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.services.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    @PostMapping("coordinators")
    public ResponseEntity<Coordinator> createCoordinator(@RequestBody Coordinator coordinator) {
        Coordinator savedCoordinator = coordinatorService.save(coordinator);
        return ResponseEntity.status(201).body(savedCoordinator);
    }

    @GetMapping("coordinators/{id}")
    public ResponseEntity<Coordinator> getCoordinatorById(@PathVariable Integer id) {
        Coordinator coordinator = coordinatorService.findById(id);
        return ResponseEntity.ok(coordinator);
    }

    @GetMapping("coordinators")
    public ResponseEntity<Page<Coordinator>> getAllCoordinators(Pageable pageable) {
        Page<Coordinator> coordinators = coordinatorService.findAll(pageable);
        return ResponseEntity.ok(coordinators);
    }

    @PutMapping("coordinators/{id}")
    public ResponseEntity<Coordinator> updateCoordinator(@PathVariable Integer id, @RequestBody Coordinator coordinator) {
        Coordinator updatedCoordinator = coordinatorService.update(id, coordinator);
        return ResponseEntity.ok(updatedCoordinator);
    }

    @DeleteMapping("coordinators/{id}")
    public ResponseEntity<Void> deleteCoordinator(@PathVariable Integer id) {
        coordinatorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}