package com.compass.desafio02.web.dto.subject;

public class SubjectHolderSubResponseDto {

    private String name;
    private String description;

    public SubjectHolderSubResponseDto() {
    }

    public SubjectHolderSubResponseDto(String name, String description) {
        this.name = name;
        this.description = description;
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
