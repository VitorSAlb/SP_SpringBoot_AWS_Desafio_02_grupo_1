package com.compass.desafio02.web.dto.course;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CourseAddCooDto {

    @NotBlank(message = "The course name is required.")
    private String nameCourse;

    @NotBlank
    @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String coordinatorEmail;

    public CourseAddCooDto() {
    }

    public CourseAddCooDto(String name, String coordinatorEmail) {
        this.nameCourse = name;
        this.coordinatorEmail = coordinatorEmail;
    }

    public @NotBlank(message = "The course name is required.") String getName() {
        return nameCourse;
    }

    public void setName(@NotBlank(message = "The course name is required.") String name) {
        this.nameCourse = name;
    }

    public @NotBlank @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String getCoordinatorEmail() {
        return coordinatorEmail;
    }

    public void setCoordinatorEmail(@NotBlank @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String coordinatorEmail) {
        this.coordinatorEmail = coordinatorEmail;
    }
}
