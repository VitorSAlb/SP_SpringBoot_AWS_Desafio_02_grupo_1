package com.compass.desafio02.web.dto.Course;

import com.compass.desafio02.web.dto.Coordinator.CoordinatorResponseDto;
import com.compass.desafio02.web.subject.SubjectResponseDto;

import java.util.List;

public class CourseResponseDto {

    private Integer id;
    private String name;
    private String description;
    private CoordinatorResponseDto coordinator;
    private List<SubjectResponseDto> subjects;

    public CourseResponseDto() {
    }

    public CourseResponseDto(Integer id, String name, String description, CoordinatorResponseDto coordinator, List<SubjectResponseDto> subjects) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinator = coordinator;
        this.subjects = subjects;
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

    public CoordinatorResponseDto getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(CoordinatorResponseDto coordinator) {
        this.coordinator = coordinator;
    }

    public List<SubjectResponseDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectResponseDto> subjects) {
        this.subjects = subjects;
    }
}
