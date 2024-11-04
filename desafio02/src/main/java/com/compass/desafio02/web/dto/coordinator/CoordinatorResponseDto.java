package com.compass.desafio02.web.dto.coordinator;

import com.compass.desafio02.domain.entities.Course;

public class CoordinatorResponseDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Course course;

    public CoordinatorResponseDto() {
    }

    public CoordinatorResponseDto(Integer id, String firstName, String lastName, String email, Course course) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
