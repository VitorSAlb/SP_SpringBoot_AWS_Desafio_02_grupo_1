package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.services.AuthService;
import com.compass.desafio02.web.dto.LoginRequestDTO;
import com.compass.desafio02.web.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping("/auth")
@Tag(name = "auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Authenticate", method ="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PostMapping
    ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO requestDTO){

        return new ResponseEntity<>(authService.authUser(requestDTO), HttpStatus.OK);

    }
}