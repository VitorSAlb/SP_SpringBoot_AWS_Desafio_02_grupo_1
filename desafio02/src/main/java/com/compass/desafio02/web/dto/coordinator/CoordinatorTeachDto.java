package com.compass.desafio02.web.dto.coordinator;

import jakarta.validation.constraints.NotBlank;

public class CoordinatorTeachDto {

    @NotBlank(message = "The subject name cannot be empty.")
    private String subjectName;

    public CoordinatorTeachDto() {
    }

    public CoordinatorTeachDto(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


}