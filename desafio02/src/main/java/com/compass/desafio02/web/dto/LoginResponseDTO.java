package com.compass.desafio02.web.dto;

public record LoginResponseDTO(
        String accessToken,
        Long expiresIn
) {
}