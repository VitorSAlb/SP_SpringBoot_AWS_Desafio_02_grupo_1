package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.CoordinatorProjection;
import com.compass.desafio02.domain.services.CoordinatorService;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.web.dto.UserPasswordDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorCreateDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorNewCourseDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorResponseDto;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.course.CourseResponseDto;
import com.compass.desafio02.web.dto.mapper.CoordinatorMapper;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
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

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CoordinatorResponseDto> createCoordinator(@RequestBody @Valid CoordinatorCreateDto coordinatorDto) {
        Coordinator coordinator = Mapper.toEntity(coordinatorDto, Coordinator.class);
        coordinator.setRole(Role.ROLE_COORDINATOR);
        coordinatorService.save(coordinator);
        return ResponseEntity.status(201).body(Mapper.toDto(coordinator, CoordinatorResponseDto.class));
    }

    @GetMapping()
    public ResponseEntity<PageableDto> getAllCoordinators(@PageableDefault(size = 5, sort = {"firstName"}) Pageable pageable) {
        Page<CoordinatorProjection> coordinators = coordinatorService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(coordinators, CoordinatorProjection.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordinatorResponseDto> getCoordinatorById(@PathVariable Integer id) {
        Coordinator coordinator = coordinatorService.findById(id);
        return ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponseDto(coordinator));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CoordinatorResponseDto> getCoordinatorByEmail(@PathVariable String email) {
        Coordinator coordinator = coordinatorService.findByEmail(email);
        return ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponseDto(coordinator));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CoordinatorResponseDto> updateCoordinator(@PathVariable Integer id, @RequestBody CoordinatorCreateDto coordinator) {
        Coordinator updatedCoordinator = coordinatorService.update(id, Mapper.toEntity(coordinator, Coordinator.class));
        return ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponseDto(updatedCoordinator));
    }

    @PatchMapping("/password/update/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Integer id, @RequestBody @Valid UserPasswordDto dto) {
        coordinatorService.editPassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordinator(@PathVariable Integer id) {
        coordinatorService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
