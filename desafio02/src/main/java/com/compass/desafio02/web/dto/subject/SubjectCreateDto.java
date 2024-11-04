package com.compass.desafio02.web.dto.subject;

import com.compass.desafio02.web.dto.professor.ProfessorNoSubjectResponseDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class SubjectCreateDto {

    @NotBlank(message = "The name of the discipline is required.")
    private String name;

    @NotBlank(message = "The description of the discipline is required.")
    private String description;

    @NotBlank(message = "The Subject needs email of Main Professor")
    private String mainProfessorEmail;
    @NotBlank(message = "The Subject needs email of Substitute Professor")
    private String substituteProfessorEmail;

    public SubjectCreateDto() {
    }

    public SubjectCreateDto(String name, String description, String mainProfessorEmail, String substituteProfessorEmail) {
        this.name = name;
        this.description = description;
        this.mainProfessorEmail = mainProfessorEmail;
        this.substituteProfessorEmail = substituteProfessorEmail;
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

    public @NotBlank(message = "The Subject needs email of Main Professor") String getMainProfessorEmail() {
        return mainProfessorEmail;
    }

    public void setMainProfessorEmail(@NotBlank(message = "The Subject needs email of Main Professor") String mainProfessorEmail) {
        this.mainProfessorEmail = mainProfessorEmail;
    }

    public @NotBlank(message = "The Subject needs email of Substitute Professor") String getSubstituteProfessorEmail() {
        return substituteProfessorEmail;
    }

    public void setSubstituteProfessorEmail(@NotBlank(message = "The Subject needs email of Substitute Professor") String substituteProfessorEmail) {
        this.substituteProfessorEmail = substituteProfessorEmail;
    }
}
