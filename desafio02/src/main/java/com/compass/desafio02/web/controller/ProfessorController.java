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
import com.compass.desafio02.web.dto.professor.ProfessorAddCourseDto;
import com.compass.desafio02.web.dto.professor.ProfessorCreateDto;
import com.compass.desafio02.web.dto.professor.ProfessorNoSubjectResponseDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;
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
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource created successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProfessorResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    public ResponseEntity<ProfessorNoSubjectResponseDto> createProfessor(@RequestBody ProfessorCreateDto professorDto) {
        Professor professor = ProfessorMapper.toEntity(professorDto);
        professor.setRole(Role.ROLE_PROFESSOR);
        professorService.save(professor);
        return ResponseEntity.status(201).body(Mapper.toDto(professor, ProfessorNoSubjectResponseDto.class));
    }

    @Operation(summary = "Retrieve Professor list",
            description = "Request requires Professor.",
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
        return ResponseEntity.ok(Mapper.toDto(professor, ProfessorResponseDto.class));
    }
    @Operation(summary = "Update a Professor",
            description = "Resource to update a new Professor linked to a update." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource update successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ProfessorResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDto> updateProfessor(@PathVariable Integer id, @RequestBody Professor professor) {
        Professor updatedProfessor = professorService.update(id, professor);
        return ResponseEntity.ok(ProfessorMapper.toDto(updatedProfessor));
    }

    @Operation(summary = "Delete a new Professor",
            description = "Resource to delete a new student linked to a registered Professor." +
                    "Request requires use.",
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

    @Operation(
            summary = "Assign Main Professor",
            description = "Assigns a professor as the main professor for a specific subject in a course.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Professor assigned as main successfully"),
                    @ApiResponse(responseCode = "404", description = "Professor or Subject not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
            }
    )
    @PostMapping("/assign-main")
    public ResponseEntity<Void> assignMainProfessor(@RequestBody @Valid ProfessorAddCourseDto dto) {
        professorService.addMainProfessor(dto.getEmailProfessor(), dto.getNameCourse(), dto.getSubjectName());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Remove Main Professor",
            description = "Removes a professor from being the main professor of a specific subject in a course.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Main professor removed successfully"),
                    @ApiResponse(responseCode = "404", description = "Professor or Subject not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
            }
    )
    @DeleteMapping("/remove-main")
    public ResponseEntity<Void> removeMainProfessor(@RequestBody @Valid ProfessorAddCourseDto dto) {
        professorService.removeMainProfessor(dto.getEmailProfessor(), dto.getNameCourse(), dto.getSubjectName());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Assign Substitute Professor",
            description = "Assigns a professor as the substitute professor for a specific subject in a course.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Professor assigned as substitute successfully"),
                    @ApiResponse(responseCode = "404", description = "Professor or Subject not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
            }
    )
    @PostMapping("/assign-substitute")
    public ResponseEntity<Void> assignSubstituteProfessor(@RequestBody @Valid ProfessorAddCourseDto dto) {
        professorService.addSubstituteProfessor(dto.getEmailProfessor(), dto.getNameCourse(), dto.getSubjectName());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Remove Substitute Professor",
            description = "Removes a professor from being the substitute professor of a specific subject in a course.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Substitute professor removed successfully"),
                    @ApiResponse(responseCode = "404", description = "Professor or Subject not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
            }
    )
    @DeleteMapping("/remove-substitute")
    public ResponseEntity<Void> removeSubstituteProfessor(@RequestBody @Valid ProfessorAddCourseDto dto) {
        professorService.removeSubstituteProfessor(dto.getEmailProfessor(), dto.getNameCourse(), dto.getSubjectName());
        return ResponseEntity.ok().build();
    }

}
