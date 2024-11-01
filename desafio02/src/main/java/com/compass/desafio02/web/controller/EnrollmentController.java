package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Enrollment;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.domain.services.EnrollmentService;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.enrollment.enrollmentCreateDto;
import com.compass.desafio02.web.dto.enrollment.EnrollmentResponseDto;
import com.compass.desafio02.web.dto.mapper.EnrollmentMapper;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

//    @PreAuthorize("hasRole('COORDINATOR')")
    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> createEnrollment(@Valid @RequestBody enrollmentCreateDto dto) {
        Student student = studentService.findByEmail(dto.getStudentEmail());
        Course course = courseService.findByName(dto.getCourseName());
        Enrollment enrollment = enrollmentService.createEnrollment(course.getId(), student.getId());
        return ResponseEntity.status(201).body(EnrollmentMapper.toDto(enrollment));
    }

    @GetMapping
    public ResponseEntity<PageableDto> getAllEnrollments(@PageableDefault(size = 5) Pageable pageable) {
        Page<Enrollment> enrollments = enrollmentService.getAllEnrollments(pageable);

        List<EnrollmentResponseDto> enrollmentDtos = enrollments.getContent().stream()
                .map(EnrollmentMapper::toDto)
                .toList();

        Page<EnrollmentResponseDto> enrollmentDtosPage = new PageImpl<>(enrollmentDtos, pageable, enrollments.getTotalElements());
        return ResponseEntity.ok(PageableMapper.toDto(enrollmentDtosPage, EnrollmentResponseDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto> getEnrollmentById(@PathVariable Integer id) {
        Enrollment enrollment = enrollmentService.findEnrollmentById(id);
        return ResponseEntity.ok(EnrollmentMapper.toDto(enrollment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Integer id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
