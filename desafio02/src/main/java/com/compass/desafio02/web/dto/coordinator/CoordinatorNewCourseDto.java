package com.compass.desafio02.web.dto.coordinator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CoordinatorNewCourseDto {

    @NotBlank(message = "Coordinator Email is required.")
    @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String CoordinatorEmail;

    @NotBlank(message = "Course Name is required.")
    private String courseName;

    public CoordinatorNewCourseDto() {
    }

    public CoordinatorNewCourseDto(String coordinatorEmail, String courseName) {
        CoordinatorEmail = coordinatorEmail;
        this.courseName = courseName;
    }

    public @NotBlank(message = "Coordinator Email is required.") @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String getCoordinatorEmail() {
        return CoordinatorEmail;
    }

    public void setCoordinatorEmail(@NotBlank(message = "Coordinator Email is required.") @Email(message = "{Email.studentCreateDto.student}", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String coordinatorEmail) {
        CoordinatorEmail = coordinatorEmail;
    }

    public @NotBlank(message = "Course Name is required.") String getCourseName() {
        return courseName;
    }

    public void setCourseName(@NotBlank(message = "Course Name is required.") String courseName) {
        this.courseName = courseName;
    }
}
