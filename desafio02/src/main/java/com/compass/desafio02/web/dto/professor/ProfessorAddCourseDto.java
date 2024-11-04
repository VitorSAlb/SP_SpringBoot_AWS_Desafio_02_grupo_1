package com.compass.desafio02.web.dto.professor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ProfessorAddCourseDto {

    @NotBlank(message = "The professor's email is required.")
    @Email(message = "Please provide a valid email address.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String emailProfessor;

    @NotBlank(message = "The course name is required.")
    private String nameCourse;

    public ProfessorAddCourseDto() {}

    public ProfessorAddCourseDto(String emailProfessor, String nameCourse) {
        this.emailProfessor = emailProfessor;
        this.nameCourse = nameCourse;
    }

    public @NotBlank(message = "The professor's email is required.") @Email(message = "Please provide a valid email address.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String getEmailProfessor() {
        return emailProfessor;
    }

    public void setEmailProfessor(@NotBlank(message = "The professor's email is required.") @Email(message = "Please provide a valid email address.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$") String emailProfessor) {
        this.emailProfessor = emailProfessor;
    }

    public @NotBlank(message = "The course name is required.") String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(@NotBlank(message = "The course name is required.") String nameCourse) {
        this.nameCourse = nameCourse;
    }
}
