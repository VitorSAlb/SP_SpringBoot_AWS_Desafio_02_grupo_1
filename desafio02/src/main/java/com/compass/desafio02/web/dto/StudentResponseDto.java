package com.compass.desafio02.web.dto;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.entities.enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentResponseDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthdate;
    private Role role;
    private String address;
    private String nameCourse;
    private List<Subject> subjects = new ArrayList<>();

    public StudentResponseDto() {
    }

    public StudentResponseDto(Integer id, String firstName, String lastName, String email, LocalDate birthdate, Role role, String address, Course course, List<Subject> subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.role = role;
        this.address = address;
        this.nameCourse = course.getName();
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
