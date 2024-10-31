package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Enrollment;
import com.compass.desafio02.domain.services.EnrollmentService;
import com.compass.desafio02.web.dto.enrollment.enrollmentCreateDto;
import com.compass.desafio02.web.dto.enrollment.EnrollmentResponseDto;
import com.compass.desafio02.web.dto.mapper.EnrollmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> createEnrollment(@Valid @RequestBody enrollmentCreateDto dto) {
        Enrollment enrollment = enrollmentService.createEnrollment(dto.getStudentId(), dto.getCourseId());
        return ResponseEntity.status(201).body(EnrollmentMapper.toDto(enrollment));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        List<EnrollmentResponseDto> enrollmentDtos = enrollments.stream()
                .map(EnrollmentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enrollmentDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<EnrollmentResponseDto> getEnrollmentById(@PathVariable Integer id) {
        Enrollment enrollment = enrollmentService.findEnrollmentById(id);
        return ResponseEntity.ok(EnrollmentMapper.toDto(enrollment));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Integer id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
