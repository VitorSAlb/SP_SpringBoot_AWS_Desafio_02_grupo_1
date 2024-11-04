package com.compass.desafio02.web.dto.subject;

import com.compass.desafio02.web.dto.course.CourseNoSubjectsNoCoordinatorResponseDto;

public class SubjectResponseNameDescriptionCourseDto {

    private Integer id;
    private String name;
    private String description;
    private CourseNoSubjectsNoCoordinatorResponseDto course;

    public SubjectResponseNameDescriptionCourseDto() {
    }

    public SubjectResponseNameDescriptionCourseDto(Integer id, String name, String description, CourseNoSubjectsNoCoordinatorResponseDto course) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.course = course;
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

    public CourseNoSubjectsNoCoordinatorResponseDto course() {
        return course;
    }

    public void setCourseNoSubjectsNoCoordinatorResponseDto(CourseNoSubjectsNoCoordinatorResponseDto course) {
        this.course = course;
    }
}
