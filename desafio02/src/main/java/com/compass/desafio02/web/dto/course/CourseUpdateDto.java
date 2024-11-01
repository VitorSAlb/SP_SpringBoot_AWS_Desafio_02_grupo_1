package com.compass.desafio02.web.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class CourseUpdateDto {

    @NotBlank(message = "The course name is required.")
    @NotEmpty
    private String name;

    @NotBlank(message = "The course description is required.")
    @NotEmpty
    private String description;

    public CourseUpdateDto() {
    }

    public CourseUpdateDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
