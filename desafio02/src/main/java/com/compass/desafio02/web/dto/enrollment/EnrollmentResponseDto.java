package com.compass.desafio02.web.dto.enrollment;

import com.compass.desafio02.web.dto.student.StudentResponseDto;
import com.compass.desafio02.web.dto.course.CourseResponseDto;

public class EnrollmentResponseDto {

    private Integer id;
    private StudentResponseDto student;
    private CourseResponseDto course;

    public EnrollmentResponseDto() {
    }

    public EnrollmentResponseDto(Integer id, StudentResponseDto student, CourseResponseDto course) {
        this.id = id;
        this.student = student;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StudentResponseDto getStudent() {
        return student;
    }

    public void setStudent(StudentResponseDto student) {
        this.student = student;
    }

    public CourseResponseDto getCourse() {
        return course;
    }

    public void setCourse(CourseResponseDto course) {
        this.course = course;
    }

}
