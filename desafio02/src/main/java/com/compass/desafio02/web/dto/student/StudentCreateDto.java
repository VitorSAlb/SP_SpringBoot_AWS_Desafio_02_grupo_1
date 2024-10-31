package com.compass.desafio02.web.dto.student;

import com.compass.desafio02.domain.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class StudentCreateDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    @NotBlank
    @Min(value = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$", message = "The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.")
    private String password;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    private String address;

    private Role role;

    public StudentCreateDto() {
    }

    public StudentCreateDto(String firstName, String lastName, String email, String password, LocalDate birthdate, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.address = address;
        this.role = Role.ROLE_STUDENT;
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

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
