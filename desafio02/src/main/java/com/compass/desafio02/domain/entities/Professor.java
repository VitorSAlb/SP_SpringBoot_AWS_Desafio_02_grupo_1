package com.compass.desafio02.domain.entities;

import com.compass.desafio02.domain.entities.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_professors")
public class Professor extends User implements Serializable {

    @OneToMany
    @Column(name = "Subjects")
    private List<Subjects> subjectsList;

    public Professor() {
    }

    public Professor(String firstName, String lastName, String email, LocalDate birthdate, List<Subjects> subjectsList) {
        super(firstName, lastName, email, birthdate, Role.ROLE_PROFESSOR);
        this.course = course;
        this.subjectsList = subjectsList;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Subjects> getSubjectsList() {
        return subjectsList;
    }

    public void setSubjectsList(List<Subjects> subjectsList) {
        this.subjectsList = subjectsList;
    }

}
