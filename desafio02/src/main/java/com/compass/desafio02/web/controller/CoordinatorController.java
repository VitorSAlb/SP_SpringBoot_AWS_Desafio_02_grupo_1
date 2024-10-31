package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.services.CoordinatorService;
import com.compass.desafio02.web.dto.Coordinator.CoordinatorCreateDto;
import com.compass.desafio02.web.dto.Coordinator.CoordinatorResponseDto;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.mapper.CoordinatorMapper;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coordinators")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    // Professor Student ---------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<CoordinatorResponseDto> createCoordinator(@RequestBody CoordinatorCreateDto coordinatorDto) {
        Coordinator coord = CoordinatorMapper.toCoodinator(coordinatorDto);
        coord.setRole(Role.ROLE_COORDINATOR);
        coordinatorService.save(coord);
        return ResponseEntity.status(201).body(CoordinatorMapper.toCoordinatorResponseDto(coord));
    }

    @GetMapping()
    public ResponseEntity<PageableDto> getAllCoordinators(@PageableDefault(size = 5, sort = {"firstName"}) Pageable pageable) {
        Page<Coordinator> coordinators = coordinatorService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(coordinators));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordinatorResponseDto> getCoordinatorById(@PathVariable Integer id) {
        Coordinator coordinator = coordinatorService.findById(id);
        return ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponseDto(coordinator));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordinatorResponseDto> updateCoordinator(@PathVariable Integer id, @RequestBody Coordinator coordinator) {
        Coordinator updatedCoordinator = coordinatorService.update(id, coordinator);
        return ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponseDto(updatedCoordinator));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordinator(@PathVariable Integer id) {
        coordinatorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // End Professor ---------------------------------------------------------------------

}
