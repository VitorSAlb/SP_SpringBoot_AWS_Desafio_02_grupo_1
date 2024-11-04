package com.compass.desafio02.domain.entities;

import com.compass.desafio02.domain.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity

public class Coordinator extends User implements Serializable {

    @JsonManagedReference()
    @OneToOne(mappedBy = "coordinator")
    private Course course;

    public Coordinator() {
    }

    public Coordinator(String firstName, String lastName, String email, LocalDate birthdate, Role role, String password) {
        super(firstName, lastName, email, birthdate, role, password);
    }

    public Coordinator(String firstName, String lastName, String email, LocalDate birthdate, String password, Course course) {
        super(firstName, lastName, email, birthdate, Role.ROLE_COORDINATOR, password);
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        if (course != null && !course.getCoordinator().equals(this)) {
            course.setCoordinator(this);
        }
    }

    public boolean hasCourse() {
        return this.course != null;
    }

}
