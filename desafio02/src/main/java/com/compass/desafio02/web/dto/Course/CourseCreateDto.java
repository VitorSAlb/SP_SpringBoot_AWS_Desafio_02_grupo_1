package com.compass.desafio02.web.dto.Course;

import jakarta.validation.constraints.NotBlank;

public class CourseCreateDto {

    @NotBlank(message = "The course name is required.")
    private String name;

    @NotBlank(message = "The course description is required.")
    private String description;

    private Integer coordinatorId;

    public CourseCreateDto() {
    }

    public CourseCreateDto(String name, String description, Integer coordinatorId) {
        this.name = name;
        this.description = description;
        this.coordinatorId = coordinatorId;
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

    public Integer getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(Integer coordinatorId) {
        this.coordinatorId = coordinatorId;
    }
}
