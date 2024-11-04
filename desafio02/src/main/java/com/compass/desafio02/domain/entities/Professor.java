package com.compass.desafio02.domain.entities;

import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.infrastructure.exceptions.BusinessRuleException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Professor extends User implements Serializable {

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "mainProfessor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "Subjects")
    private List<Subject> subjectHolder = new ArrayList<>();

    @OneToMany(mappedBy = "substituteProfessor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subject> subjectSub = new ArrayList<>();

    public Professor() {
    }

    public Professor(String firstName, String lastName, String email, LocalDate birthdate, String password) {
        super(firstName, lastName, email, birthdate, Role.ROLE_PROFESSOR, password);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Subject> getSubjectHolder() {
        return subjectHolder;
    }

    public void setSubjectHolder(List<Subject> subjectHolder) {
        this.subjectHolder = subjectHolder;
    }

    public List<Subject> getSubjectSub() {
        return subjectSub;
    }

    public void setSubjectSub(List<Subject> subjectSub) {
        this.subjectSub = subjectSub;
    }

    public void addSubjectHolder(Subject subject) {
        if (subjectHolder.isEmpty()) {
            subjectHolder.add(subject);
        } else {
            throw new BusinessRuleException("The Professor only can have one subject");
        }
    }

    public void addSubjectSub(Subject subject) {
        if (subjectSub.size() < 2) {
            subjectSub.add(subject);
        } else {
            throw new BusinessRuleException("The teacher can only be a substitute in two subjects.");
        }
    }

}
