package com.compass.desafio02.web.dto.course;

import jakarta.validation.constraints.NotBlank;

public class CourseAddSubjectDto {

    @NotBlank(message = "The course name is required.")
    private String nameCourse;

    @NotBlank(message = "The Subject name is required.")
    private String subjectName;

    public CourseAddSubjectDto() {
    }

    public CourseAddSubjectDto(String nameCourse, String subjectName) {
        this.nameCourse = nameCourse;
        this.subjectName = subjectName;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
