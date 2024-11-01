package com.compass.desafio02.web.dto.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class SubjectCreateDto {

    @NotBlank(message = "The name of the discipline is required.")
    private String name;

    @NotBlank(message = "The description of the discipline is required.")
    private String description;

    @NotBlank(message = "The Course Name is required.")
    private String courseName;

    public SubjectCreateDto() {
    }

    public SubjectCreateDto(String name, String description, String courseName) {
        this.name = name;
        this.description = description;
        this.courseName = courseName;
    }

    public @NotBlank(message = "The name of the discipline is required.") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The name of the discipline is required.") String name) {
        this.name = name;
    }

    public @NotBlank(message = "The description of the discipline is required.") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "The description of the discipline is required.") String description) {
        this.description = description;
    }

    public @NotBlank(message = "The Course Name is required.") String getCourseName() {
        return courseName;
    }

    public void setCourseName(@NotBlank(message = "The Course Name is required.") String courseName) {
        this.courseName = courseName;
    }
}
