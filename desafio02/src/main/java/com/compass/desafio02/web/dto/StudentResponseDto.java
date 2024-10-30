package com.compass.desafio02.web.dto;

import com.compass.desafio02.domain.entities.enums.Role;

import java.time.LocalDate;

public class StudentResponseDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthdate;
    private Role role;
    private String address;

    public StudentResponseDto() {
    }

    public StudentResponseDto(Integer id, String firstName, String lastName, String email, LocalDate birthdate, Role role, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.role = role;
        this.address = address;
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
}
