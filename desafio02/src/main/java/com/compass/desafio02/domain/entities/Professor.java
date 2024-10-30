package com.compass.desafio02.domain.entities;

import com.compass.desafio02.domain.entities.enums.Role;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor extends User implements Serializable {

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "holder_professor")
    @Column(name = "Subjects")
    private List<Subject> subjectHolder = new ArrayList<>();

    @OneToMany(mappedBy = "substitute_professor")
    private List<Subject> subjectSub = new ArrayList<>();

    public Professor() {
    }

    public Professor(String firstName, String lastName, String email, LocalDate birthdate, Course course) {
        super(firstName, lastName, email, birthdate, Role.ROLE_PROFESSOR);
        this.course = course;
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
            throw new IllegalStateException("The Professor only can have one subject");
        }
    }

    public void addSubjectSub(Subject subject) {
        if (subjectSub.size() < 2) {
            subjectSub.add(subject);
        } else {
            throw new IllegalStateException("The teacher can only be a substitute in two subjects.");
        }
    }

}
