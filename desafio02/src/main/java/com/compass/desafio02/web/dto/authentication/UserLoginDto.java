package com.compass.desafio02.web.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public class UserLoginDto {

    @NotBlank
    @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public @NotBlank @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String username) {
        this.username = username;
    }

    public @NotBlank @Size(min = 8) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 8) String password) {
        this.password = password;
    }
}
