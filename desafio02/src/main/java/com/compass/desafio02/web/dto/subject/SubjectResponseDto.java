package com.compass.desafio02.web.dto.subject;

import com.compass.desafio02.web.dto.course.CourseNoSubjectsResponseDto;
import com.compass.desafio02.web.dto.professor.ProfessorNoSubjectResponseDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import com.compass.desafio02.web.dto.student.StudentGResponseDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;
import com.compass.desafio02.web.dto.course.CourseResponseDto;

import java.util.List;

public class SubjectResponseDto {

    private Integer id;
    private String name;
    private String description;
    private ProfessorNoSubjectResponseDto mainProfessor;
    private ProfessorNoSubjectResponseDto substituteProfessor;
    private CourseNoSubjectsResponseDto course;
    private List<StudentGResponseDto> students;

    public SubjectResponseDto() {
    }

    public SubjectResponseDto(Integer id, String name, String description, ProfessorNoSubjectResponseDto mainProfessor, ProfessorNoSubjectResponseDto substituteProfessor, CourseNoSubjectsResponseDto course, List<StudentGResponseDto> students) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.mainProfessor = mainProfessor;
        this.substituteProfessor = substituteProfessor;
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

    public ProfessorNoSubjectResponseDto getMainProfessor() {
        return mainProfessor;
    }

    public void setMainProfessor(ProfessorNoSubjectResponseDto mainProfessor) {
        this.mainProfessor = mainProfessor;
    }

    public ProfessorNoSubjectResponseDto getSubstituteProfessor() {
        return substituteProfessor;
    }

    public void setSubstituteProfessor(ProfessorNoSubjectResponseDto substituteProfessor) {
        this.substituteProfessor = substituteProfessor;
    }

    public CourseNoSubjectsResponseDto getCourse() {
        return course;
    }

    public void setCourse(CourseNoSubjectsResponseDto course) {
        this.course = course;
    }

    public List<StudentGResponseDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentGResponseDto> students) {
        this.students = students;
    }
}
