package com.compass.desafio02.web.dto.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class SubjectCreateDto {

    @NotBlank(message = "The name of the discipline is required.")
    private String name;

    @NotBlank(message = "The description of the discipline is required.")
    private String description;

    @NotNull(message = "The description of the discipline is mandatory.")
    private Integer mainProfessor;

    @NotNull(message = "The Substitute teacher ID is required.")
    private Integer substituteProfessor;

    @NotNull(message = "The Course ID is required.")
    private Integer courseId;

    private List<Integer> studentIds;

    public SubjectCreateDto() {
    }

    public SubjectCreateDto(String name, String description, Integer mainProfessor, Integer substituteProfessor, Integer courseId, List<Integer> studentIds) {
        this.name = name;
        this.description = description;
        this.mainProfessor = mainProfessor;
        this.substituteProfessor = substituteProfessor;
        this.courseId = courseId;
        this.studentIds = studentIds;
    }

    public @NotBlank(message = "The name of the discipline is required.") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The name of the discipline is required.") String name) {
        this.name = name;
    }

    public @NotBlank(message = "The description of the discipline is required.") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "The description of the discipline is required.") String description) {
        this.description = description;
    }

    public @NotNull(message = "The description of the discipline is mandatory.") Integer getMainProfessor() {
        return mainProfessor;
    }

    public void setMainProfessor(@NotNull(message = "The description of the discipline is mandatory.") Integer mainProfessor) {
        this.mainProfessor = mainProfessor;
    }

    public @NotNull(message = "The Substitute teacher ID is required.") Integer getSubstituteProfessor() {
        return substituteProfessor;
    }

    public void setSubstituteProfessor(@NotNull(message = "The Substitute teacher ID is required.") Integer substituteProfessor) {
        this.substituteProfessor = substituteProfessor;
    }

    public @NotNull(message = "The Course ID is required.") Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(@NotNull(message = "The Course ID is required.") Integer courseId) {
        this.courseId = courseId;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }
}
