package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.services.CoordinatorService;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import com.compass.desafio02.web.dto.*;
import com.compass.desafio02.web.dto.course.*;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import com.compass.desafio02.web.dto.professor.ProfessorAddCourseDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseNameAndDescriptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.spi.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Course", description = "Contains all operations related to a Course resource")
@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @Autowired
    private CoordinatorService coordinatorService;
    @Autowired
    private ProfessorService professorService;


    @Operation(summary = "Retrieve student list",
            description = "Request requires Student.",
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
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_COORDINATOR') ")
    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, page = 0, sort = {"name"}) Pageable pageable) {
        Page<Course> courses = courseService.findAll(pageable);

        List<CourseResponseDto> courseDtos = courses.getContent().stream()
                .map(Mapper::toCourseResponseDto).toList();
        Page<CourseResponseDto> courseDtosPage = new PageImpl<>(courseDtos, pageable, courses.getTotalElements());
        return ResponseEntity.ok(PageableMapper.toDto(courseDtosPage, CourseResponseDto.class));
    }

    @Operation(summary = "Find a Course", description = "Resource to locate a Course by ID." +
            "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_COORDINATOR') ")
    public ResponseEntity<CourseResponseDto> findById(@PathVariable Integer id) {
        Course course = courseService.findById(id);
        CourseResponseDto courseResponseDto = Mapper.toCourseResponseDto(course);
        return ResponseEntity.ok(courseResponseDto);
    }

    @Operation(summary = "Find a Course", description = "Resource to locate a Course by Email." +
            "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ROLE_COORDINATOR') ")
    public ResponseEntity<CourseResponseDto> findByName(@PathVariable String name) {
        Course course = courseService.findByName(name);
        CourseResponseDto courseResponseDto = Mapper.toCourseResponseDto(course);
        return ResponseEntity.ok(courseResponseDto);
    }

    @Operation(summary = "Delete a new Course",
            description = "Resource to delete a new student linked to a registered Course." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource deleted successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Not Fount",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('ROLE_COORDINATOR') ")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        courseService.delete(name);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a new Course",
            description = "Resource to create a new Course linked to a registered user." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource created successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_COORDINATOR') ")
    public ResponseEntity<CourseNoSubjectsResponseDto> create(@RequestBody @Valid CourseCreateDto courseCreateDto) {
        Coordinator coo = coordinatorService.findByEmail(courseCreateDto.getCoordinatorEmail());

        Course course = new Course();
        course.setName(courseCreateDto.getName());
        course.setDescription(courseCreateDto.getDescription());
        course.setCoordinator(coo);

        Course savedCourse = courseService.save(course);

        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.toDto(savedCourse, CourseNoSubjectsResponseDto.class));
    }

    @Operation(summary = "Update a Course",
            description = "Resource to update a new student linked to a update password." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource update successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CourseResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })

    @PutMapping("/{name}")
    @PreAuthorize("hasRole('ROLE_COORDINATOR')")
    public ResponseEntity<CourseResponseDto> update(@PathVariable String name, @RequestBody CourseUpdateDto courseUpdateDto) {
        Course course = Mapper.toEntity(courseUpdateDto, Course.class);

        Course savedCourse = courseService.update(name, course);
        CourseResponseDto courseResponseDto = Mapper.toCourseResponseDto(savedCourse);
        return ResponseEntity.ok(courseResponseDto);
    }

    @Operation(summary = "Update a new Course",
            description = "Resource to Course." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource update successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PatchMapping("/coordinator")
    @PreAuthorize("hasRole('ROLE_COORDINATOR')")
    public ResponseEntity<CourseResponseDto> updateCoordinator(@RequestBody CourseAddCooDto dto) {
        Course updatedCourse = courseService.addCoordinatorToCourse(dto.getName(), dto.getCoordinatorEmail());
        return ResponseEntity.ok(Mapper.toCourseResponseDto(updatedCourse));
    }

    @Operation(summary = "Update a new Course",
            description = "Resource to Course." +
                    "Request requires use.'",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource update successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PatchMapping("/remove/coordinator/{name}")
    @PreAuthorize("hasRole('ROLE_COORDINATOR')")
    public ResponseEntity<Void> removeCoordinator(@PathVariable String name) {
        courseService.removeCoordinatorFromCourse(name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/add/subject")
    @PreAuthorize("hasRole('ROLE_COORDINATOR') or hasRole('ROLE_PROFESSOR')")
    public ResponseEntity<Void> addSubject(@RequestBody @Valid CourseAddSubjectDto dto) {
        courseService.addSubjectToCourse(dto.getNameCourse(), dto.getSubjectName());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/remove/subject")
    @PreAuthorize("hasRole('ROLE_COORDINATOR') or hasRole('ROLE_PROFESSOR')")
    public ResponseEntity<Void> removeSubject(@RequestBody @Valid CourseAddSubjectDto dto) {
        courseService.removeSubjectToCourse(dto.getNameCourse(), dto.getSubjectName());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/add/professor")
    @PreAuthorize("hasRole('ROLE_COORDINATOR') or hasRole('ROLE_PROFESSOR')")
    public ResponseEntity<Void> addProfessor(@RequestBody @Valid ProfessorAddCourseDto dto) {
        Professor professor = professorService.findByEmail(dto.getEmailProfessor());
        Course course = courseService.findByName(dto.getNameCourse());

        professorService.addCourse(course, professor);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/remove/professor/{email}")
    @PreAuthorize("hasRole('ROLE_COORDINATOR') or hasRole('ROLE_PROFESSOR')")
    public ResponseEntity<Void> removeProfessor(@PathVariable String email) {
        professorService.removeCourse(professorService.findByEmail(email));
        return ResponseEntity.noContent().build();
    }
}