package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.student.StudentCreateDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;
import com.compass.desafio02.web.dto.UserPasswordDto;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import com.compass.desafio02.web.dto.mapper.StudentMapper;
import com.compass.desafio02.web.dto.student.StudentUpdateDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Student", description = "Contains all operations related to a students resource")
@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

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
                    ),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping()
    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, page = 0,sort = {"firstName"}) Pageable pageable) {
        Page<StudentProjection> students = studentService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(students, StudentProjection.class));
    }

    @Operation(summary = "Find a student", description = "Resource to locate a student by ID." +
            "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> findById(@PathVariable Integer id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(Mapper.toDto(student, StudentResponseDto.class));
    }

    @Operation(summary = "Find a student", description = "Resource to locate a student by Email." +
            "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/email/{email}")
    public ResponseEntity<StudentResponseDto> findByEmail(@PathVariable String email) {
        Student student = studentService.findByEmail(email);
        return ResponseEntity.ok(Mapper.toDto(student, StudentResponseDto.class));
    }

    @Operation(summary = "Create a new student",
            description = "Resource to create a new student linked to a registered user. " +
                    "Request requires use of a bearer token. Restricted access to Role='ROLE_PROFESSOR'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource created successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping()
    public ResponseEntity<StudentResponseDto> create(@RequestBody @Valid StudentCreateDto dto) {
        Student student = StudentMapper.toEntity(dto);
        student.setRole(Role.ROLE_STUDENT);
        studentService.save(student);
        return ResponseEntity.status(201).body(Mapper.toDto(student, StudentResponseDto.class));
    }

    @Operation(summary = "Update a new student",
            description = "Resource to update a new student linked to a update password. " +
                    "Request requires use of a bearer token. Restricted access to Role='ROLE_PROFESSOR'",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource update successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/update/{email}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable String email, @RequestBody StudentUpdateDto dto) {
        Student student = studentService.update(email, Mapper.toEntity(dto, Student.class));
        return ResponseEntity.ok(Mapper.toDto(student, StudentResponseDto.class));
    }

    @Operation(summary = "Update a new student",
            description = "Resource to update a new student linked to a update password. " +
                    "Request requires use of a bearer token. Restricted access to Role='ROLE_PROFESSOR'",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource deleted successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Student not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/password/update/{email}")
    public ResponseEntity<Void> updatePassword(@PathVariable String email, @RequestBody @Valid UserPasswordDto dto) {
        studentService.editPassword(email, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a new student",
            description = "Resource to delete a new student linked to a registered user. " +
                    "Request requires use of a bearer token. Restricted access to Role='ROLE_PROFESSOR'",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource deleted successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = StudentResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Not Fount",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        studentService.delete(email);
        return ResponseEntity.noContent().build();
    }
}
