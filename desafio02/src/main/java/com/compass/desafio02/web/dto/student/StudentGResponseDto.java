package com.compass.desafio02.web.dto.student;

public class StudentGResponseDto {

    private Integer id;
    private String firstName;
    private String email;

    public StudentGResponseDto() {
    }

    public StudentGResponseDto(Integer id, String firstName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
