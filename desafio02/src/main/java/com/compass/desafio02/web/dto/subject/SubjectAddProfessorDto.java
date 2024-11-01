package com.compass.desafio02.web.dto.subject;

import jakarta.validation.constraints.NotBlank;

public class SubjectAddProfessorDto {

    @NotBlank(message = "Email professor is required")
    private String emailProfessor;

    public SubjectAddProfessorDto() {
    }

    public SubjectAddProfessorDto(String emailProfessor) {
        this.emailProfessor = emailProfessor;
    }

    public @NotBlank(message = "Email professor is required") String getEmailProfessor() {
        return emailProfessor;
    }

    public void setEmailProfessor(@NotBlank(message = "Email professor is required") String emailProfessor) {
        this.emailProfessor = emailProfessor;
    }
}
