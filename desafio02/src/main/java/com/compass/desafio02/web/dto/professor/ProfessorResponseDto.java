package com.compass.desafio02.web.dto.professor;

import com.compass.desafio02.web.dto.subject.SubjectResponseDto;

import java.util.List;

public class ProfessorResponseDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private List<SubjectResponseDto> subjects;

    public ProfessorResponseDto() {
    }

    public ProfessorResponseDto(Integer id, String firstName, String lastName, String email, List<SubjectResponseDto> subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.subjects = subjects;
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

    public List<SubjectResponseDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectResponseDto> subjects) {
        this.subjects = subjects;
    }
}
