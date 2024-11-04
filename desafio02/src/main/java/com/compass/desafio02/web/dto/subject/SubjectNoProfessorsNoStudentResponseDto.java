package com.compass.desafio02.web.dto.subject;

import com.compass.desafio02.web.dto.course.CourseNoSubjectsResponseDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;

import java.util.List;

public class SubjectNoProfessorsNoStudentResponseDto {

    private Integer id;
    private String name;
    private String description;
    private CourseNoSubjectsResponseDto course;

    public SubjectNoProfessorsNoStudentResponseDto() {
    }

    public SubjectNoProfessorsNoStudentResponseDto(Integer id, String name, String description, CourseNoSubjectsResponseDto course) {
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

    public CourseNoSubjectsResponseDto getCourse() {
        return course;
    }

    public void setCourse(CourseNoSubjectsResponseDto course) {
        this.course = course;
    }
}
