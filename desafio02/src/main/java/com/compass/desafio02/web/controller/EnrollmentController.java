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
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.spi.ErrorMessage;
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

@Tag(name = "Enrollments", description = "Contains all operations related to a enrollments resource")
@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Create a new Enrollment",
            description = "Resource to create a new Enrollment linked to a registered user." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource created successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = EnrollmentResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_COORDINATOR')")
    public ResponseEntity<Void> createEnrollment(@Valid @RequestBody enrollmentCreateDto dto) {
        Student student = studentService.findByEmail(dto.getStudentEmail());
        Course course = courseService.findByName(dto.getCourseName());
        Enrollment enrollment = enrollmentService.createEnrollment(course.getId(), student.getId());
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Retrieve Enrollment list",
            description = "Request requires Enrollment.",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Represents the returned page"
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5")),
                            description = "Represents the total number of elements per page"
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "firstName,asc")),
                            description = "Represents the ordering of results. Multiple sorting criteria are supported."
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PageableDto.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "I don't allow this feature.",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_COORDINATOR', 'ROLE_PROFESSOR')")
    public ResponseEntity<PageableDto> getAllEnrollments(@PageableDefault(size = 5) Pageable pageable) {
        Page<Enrollment> enrollments = enrollmentService.getAllEnrollments(pageable);

        List<EnrollmentResponseDto> enrollmentDtos = enrollments.getContent().stream()
                .map(EnrollmentMapper::toDto)
                .toList();

        Page<EnrollmentResponseDto> enrollmentDtosPage = new PageImpl<>(enrollmentDtos, pageable, enrollments.getTotalElements());
        return ResponseEntity.ok(PageableMapper.toDto(enrollmentDtosPage, EnrollmentResponseDto.class));
    }

    @Operation(summary = "Find a Enrollment", description = "Resource to locate a Enrollment by ID." +
            "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = EnrollmentResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Enrollment not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_COORDINATOR', 'ROLE_PROFESSOR', 'ROLE_STUDENT')")
    public ResponseEntity<EnrollmentResponseDto> getEnrollmentById(@PathVariable Integer id) {
        Enrollment enrollment = enrollmentService.findEnrollmentById(id);
        return ResponseEntity.ok(EnrollmentMapper.toDto(enrollment));
    }

    @Operation(summary = "Delete a new Enrollment",
            description = "Resource to delete a new student linked to a registered Enrollment." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource deleted successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = EnrollmentResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Not Fount",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_COORDINATOR')")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Integer id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ROLE_COORDINATOR', 'ROLE_PROFESSOR')")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByCourseId(@PathVariable Integer courseId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
        List<EnrollmentResponseDto> enrollmentDtos = enrollments.stream()
                .map(EnrollmentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enrollmentDtos);
    }

    @Operation(summary = "List enrollments by student ID", description = "Retrieve all enrollments associated with a specific student.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Enrollments retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnrollmentResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Student not found or no enrollments for this student",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_COORDINATOR', 'ROLE_PROFESSOR', 'ROLE_STUDENT')")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByStudentId(@PathVariable Integer studentId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        List<EnrollmentResponseDto> enrollmentDtos = enrollments.stream()
                .map(EnrollmentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enrollmentDtos);
    }
}