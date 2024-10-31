package com.compass.desafio02.web.dto.enrollment;

import jakarta.validation.constraints.NotNull;

public class enrollmentCreateDto {

    @NotNull(message = "Student ID is required.")
    private Integer studentId;

    @NotNull(message = "Course ID is required.")
    private Integer courseId;

    public enrollmentCreateDto() {
    }

    public enrollmentCreateDto(Integer studentId, Integer courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public @NotNull(message = "Student ID is required.") Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(@NotNull(message = "Student ID is required.") Integer studentId) {
        this.studentId = studentId;
    }

    public @NotNull(message = "Course ID is required.") Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(@NotNull(message = "Course ID is required.") Integer courseId) {
        this.courseId = courseId;
    }

}
