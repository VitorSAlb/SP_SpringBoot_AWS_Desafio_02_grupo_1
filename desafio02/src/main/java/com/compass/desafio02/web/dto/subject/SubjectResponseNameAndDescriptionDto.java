package com.compass.desafio02.web.dto.subject;

import com.compass.desafio02.web.dto.course.CourseNoSubjectsResponseDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;

import java.util.List;

public class SubjectResponseNameAndDescriptionDto {

    private Integer id;
    private String name;
    private String description;

    public SubjectResponseNameAndDescriptionDto() {
    }

    public SubjectResponseNameAndDescriptionDto(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
