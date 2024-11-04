package com.compass.desafio02.web.dto.course;

import com.compass.desafio02.web.dto.coordinator.CoordinatorNoCourseResponseDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseNoCourseDto;


import java.util.List;

public class CourseResponseDto {

    private Integer id;
    private String name;
    private String description;
    private CoordinatorNoCourseResponseDto coordinator;
    private List<SubjectResponseNoCourseDto> subjects;

    public CourseResponseDto() {
    }

    public CourseResponseDto(Integer id, String name, String description, CoordinatorNoCourseResponseDto coordinator, List<SubjectResponseNoCourseDto> subjects) {
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

    public CoordinatorNoCourseResponseDto getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(CoordinatorNoCourseResponseDto coordinator) {
        this.coordinator = coordinator;
    }

    public List<SubjectResponseNoCourseDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectResponseNoCourseDto> subjects) {
        this.subjects = subjects;
    }
}
