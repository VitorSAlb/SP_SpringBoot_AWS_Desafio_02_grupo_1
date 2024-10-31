package com.compass.desafio02.web.dto.course;

import jakarta.validation.constraints.NotBlank;

public class CourseCreateDto {

    @NotBlank(message = "The course name is required.")
    private String name;

    @NotBlank(message = "The course description is required.")
    private String description;

    private String coordinatorEmail;

    public CourseCreateDto() {
    }

    public CourseCreateDto(String name, String description, String coordinatorEmail) {
        this.name = name;
        this.description = description;
        this.coordinatorEmail = coordinatorEmail;
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

    public String getCoordinatorEmail() {
        return coordinatorEmail;
    }

    public void setCoordinatorId(String coordinatorEmail) {
        this.coordinatorEmail = coordinatorEmail;
    }
}
