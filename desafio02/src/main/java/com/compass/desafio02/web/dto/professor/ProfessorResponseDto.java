package com.compass.desafio02.web.dto.professor;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.web.dto.course.CourseNoSubjectsResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectHolderSubResponseDto;

import java.util.List;

public class ProfessorResponseDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private List<SubjectHolderSubResponseDto> MainSubjects;
    private List<SubjectHolderSubResponseDto> SubstituteSubjects;
    private CourseNoSubjectsResponseDto course;

    public ProfessorResponseDto() {
    }

    public ProfessorResponseDto(Integer id, String firstName, String lastName, String email, List<SubjectHolderSubResponseDto> mainSubjects, List<SubjectHolderSubResponseDto> substituteSubjects, CourseNoSubjectsResponseDto course) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        MainSubjects = mainSubjects;
        SubstituteSubjects = substituteSubjects;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SubjectHolderSubResponseDto> getMainSubjects() {
        return MainSubjects;
    }

    public void setMainSubjects(List<SubjectHolderSubResponseDto> mainSubjects) {
        MainSubjects = mainSubjects;
    }

    public List<SubjectHolderSubResponseDto> getSubstituteSubjects() {
        return SubstituteSubjects;
    }

    public void setSubstituteSubjects(List<SubjectHolderSubResponseDto> substituteSubjects) {
        SubstituteSubjects = substituteSubjects;
    }

    public CourseNoSubjectsResponseDto getCourse() {
        return course;
    }

    public void setCourse(CourseNoSubjectsResponseDto course) {
        this.course = course;
    }
}
