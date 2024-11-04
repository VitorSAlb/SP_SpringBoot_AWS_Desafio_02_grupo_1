package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.CoordinatorProjection;
import com.compass.desafio02.domain.services.CoordinatorService;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.web.dto.UserPasswordDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorCreateDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorResponseDto;
import com.compass.desafio02.web.dto.PageableDto;
//import com.compass.desafio02.web.dto.coordinator.CoordinatorTeachDto;
import com.compass.desafio02.web.dto.mapper.CoordinatorMapper;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Coordinators", description = "Contains all operations related to a coordinators resource")
@RestController
@RequestMapping("/api/v1/coordinators")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Create a new Coordinator",
            description = "Resource to create a new Coordinator. " +
                    "Request requires use.'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource created successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    public ResponseEntity<CoordinatorResponseDto> createCoordinator(@RequestBody @Valid CoordinatorCreateDto coordinatorDto) {
        Coordinator coordinator = Mapper.toEntity(coordinatorDto, Coordinator.class);
        coordinator.setRole(Role.ROLE_COORDINATOR);
        coordinatorService.save(coordinator);
        return ResponseEntity.status(201).body(Mapper.toDto(coordinator, CoordinatorResponseDto.class));
    }

    @Operation(summary = "Retrieve Coordinators list",
            description = "Request requires Coordinators.",
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
    public ResponseEntity<PageableDto> getAllCoordinators(@PageableDefault(size = 5, sort = {"firstName"}) Pageable pageable) {
        Page<CoordinatorProjection> coordinators = coordinatorService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(coordinators, CoordinatorProjection.class));
    }

    @Operation(summary = "Find a Coordinators", description = "Resource to locate a Coordinators by ID." +
            "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<CoordinatorResponseDto> getCoordinatorById(@PathVariable Integer id) {
        Coordinator coordinator = coordinatorService.findById(id);
        return ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponseDto(coordinator));
    }

    @Operation(summary = "Find a Coordinators", description = "Resource to locate a Coordinators by Email." +
            "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource located successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Course not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Feature not allowed",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/email/{email}")
    public ResponseEntity<CoordinatorResponseDto> getCoordinatorByEmail(@PathVariable String email) {
        Coordinator coordinator = coordinatorService.findByEmail(email);
        return ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponseDto(coordinator));
    }

    @Operation(summary = "Update a new Coordinator",
            description = "Resource to update. " +
                    "Request requires use of a bearer token. Restricted access to Role='ROLE_PROFESSOR'",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource update successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Coordinator not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PutMapping("/update/{email}")
    public ResponseEntity<CoordinatorResponseDto> updateCoordinator(@PathVariable String email, @RequestBody CoordinatorCreateDto coordinator) {
        Coordinator updatedCoordinator = coordinatorService.update(email, Mapper.toEntity(coordinator, Coordinator.class));

        return ResponseEntity.ok(CoordinatorMapper.toCoordinatorResponseDto(updatedCoordinator));
    }

    @Operation(summary = "Update a new Coordinator",
            description = "Resource to update. Update Password" +
                    "Request requires use of a bearer token. Restricted access to Role='ROLE_PROFESSOR'",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource update successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Coordinator not found",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PatchMapping("/password/update/{email}")
    public ResponseEntity<Void> updatePassword(@PathVariable String email, @RequestBody @Valid UserPasswordDto dto) {
        coordinatorService.editPassword(email, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a new Coordinators",
            description = "Resource to delete a new Coordinators linked to a registered Coordinators." +
                    "Request requires use.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource deleted successfully",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = CoordinatorResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Resource not processed due to missing or invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Not Fount",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteCoordinator(@PathVariable String email) {
        coordinatorService.delete(email);

        return ResponseEntity.noContent().build();
    }

//    @PatchMapping("/api/v1/coordinators/assignAsProfessor")
//    public ResponseEntity<String> assignCoordinatorAsProfessor(
//            @Validated @RequestBody CoordinatorTeachDto coordinatorTeachDto,
//            @RequestParam boolean isMainProfessor) {
//
//        coordinatorService.assignCoordinatorAsProfessor(
//                coordinatorTeachDto.getSubjectName(),
//                coordinatorTeachDto.getCoordinatorEmail(),
//                isMainProfessor
//        );
//
//        String role = isMainProfessor ? "main professor" : "substitute professor";
//        return ResponseEntity.ok("Coordinator assigned as " + role + " successfully.");
//    }

}
