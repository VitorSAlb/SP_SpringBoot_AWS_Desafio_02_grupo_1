package com.compass.desafio02.web.subject;

import com.compass.desafio02.web.dto.Course.CourseResponseDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;

public class SubjectResponseDto {

    private Integer id;
    private String name;
    private String description;
    private ProfessorResponseDto mainProfessor;
    private ProfessorResponseDto substituteProfessor;
    private CourseResponseDto course;

    public SubjectResponseDto() {
    }

    public SubjectResponseDto(Integer id, String name, String description, ProfessorResponseDto mainProfessor, ProfessorResponseDto substituteProfessor, CourseResponseDto course) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.mainProfessor = mainProfessor;
        this.substituteProfessor = substituteProfessor;
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

    public ProfessorResponseDto getMainProfessor() {
        return mainProfessor;
    }

    public void setMainProfessor(ProfessorResponseDto mainProfessor) {
        this.mainProfessor = mainProfessor;
    }

    public ProfessorResponseDto getSubstituteProfessor() {
        return substituteProfessor;
    }

    public void setSubstituteProfessor(ProfessorResponseDto substituteProfessor) {
        this.substituteProfessor = substituteProfessor;
    }

    public CourseResponseDto getCourse() {
        return course;
    }

    public void setCourse(CourseResponseDto course) {
        this.course = course;
    }

}
