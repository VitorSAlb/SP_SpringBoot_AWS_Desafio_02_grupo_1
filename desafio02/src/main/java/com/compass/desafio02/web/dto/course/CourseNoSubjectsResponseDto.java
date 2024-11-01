package com.compass.desafio02.web.dto.course;

import com.compass.desafio02.web.dto.coordinator.CoordinatorNoCourseResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;

import java.util.List;

public class CourseNoSubjectsResponseDto {

    private Integer id;
    private String name;
    private String description;
    private CoordinatorNoCourseResponseDto coordinator;

    public CourseNoSubjectsResponseDto() {
    }

    public CourseNoSubjectsResponseDto(Integer id, String name, String description, CoordinatorNoCourseResponseDto coordinator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinator = coordinator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public CoordinatorNoCourseResponseDto getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(CoordinatorNoCourseResponseDto coordinator) {
        this.coordinator = coordinator;
    }
}
