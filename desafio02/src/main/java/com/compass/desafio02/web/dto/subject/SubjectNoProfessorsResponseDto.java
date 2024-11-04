package com.compass.desafio02.web.dto.subject;

import com.compass.desafio02.web.dto.course.CourseNoSubjectsResponseDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;

import java.util.List;

public class SubjectNoProfessorsResponseDto {

    private Integer id;
    private String name;
    private String description;
    private CourseNoSubjectsResponseDto course;
    private List<StudentResponseDto> students;

    public SubjectNoProfessorsResponseDto() {
    }

    public SubjectNoProfessorsResponseDto(Integer id, String name, String description, CourseNoSubjectsResponseDto course, List<StudentResponseDto> students) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.course = course;
        this.students = students;
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

    public List<StudentResponseDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentResponseDto> students) {
        this.students = students;
    }
}
