package com.compass.desafio02.web.dto.professor;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class ProfessorUpdateDto {

    @NotBlank(message = "First name is required.")
    @Pattern(regexp = "^[A-Z].*", message = "First name must start with a capital letter.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Pattern(regexp = "^[A-Z].*", message = "The last name must start with a capital letter.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be a valid format.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;


    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    public ProfessorUpdateDto() {}

    public ProfessorUpdateDto(String firstName, String lastName, String email,  LocalDate birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
    }

    public @NotBlank String getFirstName() {return firstName;}

    public void setFirstName(@NotBlank String firstName) {this.firstName = firstName;}

    public @NotBlank String getLastName(){return lastName;}

    public void setLastName(@NotBlank String lastName) {this.lastName = lastName;}

    public @NotBlank @Email(message = "{Email.ProfessorCreateDto.professor}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String getEmail() {return email;}

    public void setEmail(@NotBlank @Email(message = "{Email.ProfessorCreateDto.professor}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String email) {this.email = email;}

    public @NotNull LocalDate getBirthdate() {return birthdate;}

    public void setBirthdate(@NotNull LocalDate birthdate) {this.birthdate = birthdate;}
}
