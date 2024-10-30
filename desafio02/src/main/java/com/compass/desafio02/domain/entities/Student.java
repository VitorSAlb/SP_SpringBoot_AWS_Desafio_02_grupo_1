package com.compass.desafio02.domain.entities;

import com.compass.desafio02.domain.entities.enums.Role;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_students")
public class Student extends User implements Serializable {

    @Column(name = "address", nullable = false)
    private String address;

    @OneToOne
    private Course course;

    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects = new ArrayList<>();

    public Student() {
    }

    public Student(String firstName, String lastName, String email, LocalDate birthdate, String address) {
        super(firstName, lastName, email, birthdate, Role.ROLE_STUDENT);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }




}
