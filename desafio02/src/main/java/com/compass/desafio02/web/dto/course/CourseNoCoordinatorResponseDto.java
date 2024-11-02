package com.compass.desafio02.web.dto.course;

import com.compass.desafio02.web.dto.subject.SubjectResponseNameAndDescriptionDto;

import java.util.List;

public class CourseNoCoordinatorResponseDto {

    private Integer id;
    private String name;
    private String description;
    private List<SubjectResponseNameAndDescriptionDto> subjects;

    public CourseNoCoordinatorResponseDto() {
    }

    public CourseNoCoordinatorResponseDto(Integer id, String name, String description, List<SubjectResponseNameAndDescriptionDto> subjects) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public List<SubjectResponseNameAndDescriptionDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectResponseNameAndDescriptionDto> subjects) {
        this.subjects = subjects;
    }
}
