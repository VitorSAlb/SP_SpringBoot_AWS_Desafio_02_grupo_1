package com.compass.desafio02.domain.entities;

import com.compass.desafio02.domain.entities.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_students")
public class Student extends User implements Serializable {

    @Column(name = "address", nullable = false)
    private String address;

//    @OneToOne
//    @Column(name = "course_id")
//    private Course course;

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


}
