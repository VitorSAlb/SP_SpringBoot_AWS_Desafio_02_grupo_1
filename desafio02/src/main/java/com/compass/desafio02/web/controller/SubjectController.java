package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.*;
import com.compass.desafio02.domain.services.SubjectService;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.infrastructure.exceptions.CoordinatorAlreadyAssignedException;
import com.compass.desafio02.infrastructure.exceptions.CoordinatorOrCourseNotFoundException;
import com.compass.desafio02.web.dto.course.CourseResponseDto;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.subject.*;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import com.compass.desafio02.web.dto.mapper.SubjectMapper;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Subjects", description = "Contains all operations related to a Subject resource")
@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Retrieve Subjects list",
            description = "Request requires Subjects.",
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, page = 0, sort = {"name"}) Pageable pageable) {
        Page<Subject> subjects = subjectService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(subjects, SubjectResponseNameDescriptionCourseDto.class));
    }

    @Operation(summary = "Find a Subjects", description = "Resource to locate a Subjects by Name." +
            "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = SubjectResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Subjects not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SubjectResponseDto> findByName(@PathVariable String name) {
        Subject subject = subjectService.findByName(name);
        return ResponseEntity.ok(Mapper.toDto(subject, SubjectResponseDto.class));
    }

    @Operation(summary = "Create a new Subjects",
            description = "Resource to create a new subjects linked to a registered user." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource created successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = SubjectResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> create(@RequestBody @Valid SubjectCreateDto dto) {
        Professor mainProf = professorService.findByEmail(dto.getMainProfessorEmail());
        Professor subProf = professorService.findByEmail(dto.getSubstituteProfessorEmail());
        Subject subject = new Subject(dto.getName(), dto.getDescription(), mainProf, subProf, null);
        Subject savedSubject = subjectService.save(subject);
        return ResponseEntity.status(201).build();
    }


    @Operation(summary = "Delete a new Course",
            description = "Resource to delete a new student linked to a registered Course." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource deleted successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = SubjectResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Not Fount",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{name}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        subjectService.delete(name);
        return ResponseEntity.noContent().build();
    }

}

