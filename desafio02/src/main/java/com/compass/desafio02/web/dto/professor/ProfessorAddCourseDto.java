package com.compass.desafio02.web.dto.professor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ProfessorAddCourseDto {

    @NotBlank(message = "The professor's email is required.")
    @Email(message = "Please provide a valid email address.")
    private String emailProfessor;

    @NotBlank(message = "The course name is required.")
    private String nameCourse;

    @NotBlank(message = "The subject name is required.")
    private String subjectName;

    public ProfessorAddCourseDto() {}

    public ProfessorAddCourseDto(String emailProfessor, String nameCourse, String subjectName) {
        this.emailProfessor = emailProfessor;
        this.nameCourse = nameCourse;
        this.subjectName = subjectName;
    }

    public String getEmailProfessor() {
        return emailProfessor;
    }

    public void setEmailProfessor(String emailProfessor) {
        this.emailProfessor = emailProfessor;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
