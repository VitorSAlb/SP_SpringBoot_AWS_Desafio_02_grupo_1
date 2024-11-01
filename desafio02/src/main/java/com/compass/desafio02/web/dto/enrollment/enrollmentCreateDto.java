package com.compass.desafio02.web.dto.enrollment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class enrollmentCreateDto {

    @NotBlank(message = "Student email is required.")
    private String studentEmail;

    @NotBlank(message = "Course Name is required.")
    private String courseName;

    public enrollmentCreateDto() {
    }

    public enrollmentCreateDto(String studentEmail, String courseName) {
        this.studentEmail = studentEmail;
        this.courseName = courseName;
    }

    public @NotNull(message = "Student Email is required.") String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(@NotNull(message = "Student Email is required.") String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public @NotNull(message = "Course Name is required.") String getCourseName() {
        return courseName;
    }

    public void setCourseName(@NotNull(message = "Course Name is required.") String courseName) {
        this.courseName = courseName;
    }

}
