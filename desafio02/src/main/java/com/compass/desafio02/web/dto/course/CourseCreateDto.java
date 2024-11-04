package com.compass.desafio02.web.dto.course;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class CourseCreateDto {

    @NotBlank(message = "The course name is required.")
    @NotEmpty
    private String name;

    @NotBlank(message = "The course description is required.")
    @NotEmpty
    private String description;

    @NotBlank
    @Email(message = "Email is required", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String coordinatorEmail;

    public CourseCreateDto() {
    }

    public CourseCreateDto(String name, String description, String coordinatorEmail) {
        this.name = name;
        this.description = description;
        this.coordinatorEmail = coordinatorEmail;
    }

    public @NotBlank(message = "The course name is required.") @NotEmpty String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The course name is required.") @NotEmpty String name) {
        this.name = name;
    }

    public @NotBlank(message = "The course description is required.") @NotEmpty String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "The course description is required.") @NotEmpty String description) {
        this.description = description;
    }

    public @NotBlank @Email(message = "Email is required", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String getCoordinatorEmail() {
        return coordinatorEmail;
    }

    public void setCoordinatorEmail(@NotBlank @Email(message = "Email is required", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String coordinatorEmail) {
        this.coordinatorEmail = coordinatorEmail;
    }
}
