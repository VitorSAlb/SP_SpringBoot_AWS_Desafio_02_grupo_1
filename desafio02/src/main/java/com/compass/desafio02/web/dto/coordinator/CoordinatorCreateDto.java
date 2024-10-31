package com.compass.desafio02.web.dto.coordinator;

import com.compass.desafio02.domain.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CoordinatorCreateDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    public CoordinatorCreateDto() {}

    public CoordinatorCreateDto(String firstName, String lastName, String email, String password, LocalDate birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    public @NotBlank String getFirstName() {return firstName;}

    public void setFirstName(@NotBlank String firstName) {this.firstName = firstName;}

    public @NotBlank String getLastName(){return lastName;}

    public void setLastName(@NotBlank String lastName) {this.lastName = lastName;}

    public @NotBlank @Email(message = "{Email.CoordinatorCreateDto.coordinator}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String getEmail() {return email;}

    public void setEmail(@NotBlank @Email(message = "{Email.CoordinatorCreateDto.coordinator}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String email) {this.email = email;}

    public @NotBlank String getPassword() {return password;}

    public void setPassword(@NotBlank String password) {this.password = password;}

    public @NotBlank LocalDate getBirthdate() {return birthdate;}

    public void setBirthdate(@NotBlank LocalDate birthdate) {this.birthdate = birthdate;}
}
