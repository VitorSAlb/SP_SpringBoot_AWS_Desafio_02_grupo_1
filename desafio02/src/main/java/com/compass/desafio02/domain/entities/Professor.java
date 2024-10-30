package com.compass.desafio02.domain.entities;

import com.compass.desafio02.domain.entities.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Professor extends User implements Serializable {

    @OneToMany
    @Column(name = "Subjects")
    private List<Subject> subjectList = new ArrayList<>();

    public Professor() {
    }

    public Professor(String firstName, String lastName, String email, LocalDate birthdate) {
        super(firstName, lastName, email, birthdate, Role.ROLE_PROFESSOR);
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    //Alterado o nome de 'subjects...' para o singular 'subject' por medidas de boas praticas

}
