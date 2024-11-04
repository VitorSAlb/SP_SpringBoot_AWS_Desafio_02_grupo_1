package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.ProfessorProjection;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.infrastructure.exceptions.user.UserNotFoundException;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import com.compass.desafio02.web.dto.mapper.ProfessorMapper;
import com.compass.desafio02.web.dto.professor.*;
import com.compass.desafio02.web.dto.student.StudentResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Professor", description = "Contains all operations related to a Professor resource")
@RestController
@RequestMapping("/api/v1/professors")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Operation(summary = "Create a new Professor",
            description = "Resource to create a new professor linked to a registered user." +
                    "Request requires use.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource created successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProfessorResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    public ResponseEntity<ProfessorNoSubjectResponseDto> createProfessor(@RequestBody @Valid ProfessorCreateDto professorDto) {
        Professor professor = ProfessorMapper.toEntity(professorDto);
        professor.setRole(Role.ROLE_PROFESSOR);
        professorService.save(professor);
        return ResponseEntity.status(201).body(Mapper.toDto(professor, ProfessorNoSubjectResponseDto.class));
    }

    @Operation(summary = "Retrieve Professor list",
            description = "Request requires Professor.",
            security = @SecurityRequirement(name = "security"),
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
    public ResponseEntity<PageableDto> getAllProfessors(@PageableDefault(size = 5, sort = {"firstName"}) Pageable pageable) {
        Page<ProfessorProjection> professors = professorService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(professors, ProfessorProjection.class));
    }

    @Operation(summary = "Find a Professor", description = "Resource to locate a professor by ID." +
            "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProfessorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Professor not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDto> getProfessorById(@PathVariable Integer id) {
        Professor professor = professorService.findById(id);
        return ResponseEntity.ok(Mapper.toDto(professor, ProfessorResponseDto.class));
    }

    @Operation(summary = "Find a Professor", description = "Resource to locate a professor by Email." +
            "Request requires use.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProfessorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Professor not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/email/{email}")
    public ResponseEntity<ProfessorResponseDto> getProfessorByEmail(@PathVariable String email) {
        Professor professor = professorService.findByEmail(email);
        return ResponseEntity.ok(ProfessorMapper.toDto(professor));
    }
    @Operation(summary = "Update a Professor",
            description = "Resource to update a new Professor linked to a update." +
                    "Request requires use.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource update successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProfessorResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PutMapping("/update/{email}")
    public ResponseEntity<ProfessorResponseDto> updateProfessor(@PathVariable String email, @RequestBody ProfessorUpdateDto dto) {
        Professor updatedProfessor = professorService.update(email, Mapper.toDto(dto, Professor.class));
        return ResponseEntity.ok(ProfessorMapper.toDto(updatedProfessor));
    }

    @Operation(summary = "Delete a new Professor",
            description = "Resource to delete a new student linked to a registered Professor." +
                    "Request requires use.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource deleted successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProfessorResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Not Fount",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String email) {
        professorService.delete(email);
        return ResponseEntity.noContent().build();
    }

}
