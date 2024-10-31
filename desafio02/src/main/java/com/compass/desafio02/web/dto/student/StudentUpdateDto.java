package com.compass.desafio02.web.dto.student;

import com.compass.desafio02.domain.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class StudentUpdateDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private String address;

    public StudentUpdateDto() {
    }

    public StudentUpdateDto(String firstName, String lastName, String email, LocalDate birthdate, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.address = address;
    }

    public @NotBlank String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String email) {
        this.email = email;
    }

    public @NotBlank LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(@NotBlank LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public @NotBlank String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank String address) {
        this.address = address;
    }
}
