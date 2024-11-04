package com.compass.desafio02.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(

        @NotBlank
        @NotNull
        @Email
        String email,

        @NotBlank
        @NotNull
        String password
) {
}