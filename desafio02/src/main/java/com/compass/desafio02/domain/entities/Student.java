package com.compass.desafio02.domain.entities;

import com.compass.desafio02.domain.entities.enums.Role;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends User implements Serializable {

    @Column(name = "address")
    private String address;

    @ManyToOne
    private Course course;

    @ManyToMany(mappedBy = "students", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Subject> subjects = new ArrayList<>();

    public Student() {
    }

    public Student(String firstName, String lastName, String email, LocalDate birthdate, String password, String address) {
        super(firstName, lastName, email, birthdate, Role.ROLE_STUDENT, password);
        this.address = address;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (this.course != null) {
            this.course.getStudents().remove(this);
        }
        this.course = course;
        if (course != null && !course.getStudents().contains(this)) {
            course.addStudent(this);
        }
    }


    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            this.subjects.add(subject);
            subject.getStudents().add(this);
        }
    }

    public void removeSubject(Subject subject) {
        if (subjects.remove(subject)) {
            subject.getStudents().remove(this);
        }
    }

}
