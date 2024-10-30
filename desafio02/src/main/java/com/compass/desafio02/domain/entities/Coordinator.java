package com.compass.desafio02.domain.entities;

import com.compass.desafio02.domain.entities.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity

public class Coordinator extends User implements Serializable {

    @OneToOne
    private Course course;

    public Coordinator() {
    }

    public Coordinator(Integer id, String firstName, String lastName, String email, LocalDate birthdate, String password, Course course) {
        super(id, firstName, lastName, email, birthdate, Role.ROLE_COORDINATOR, password);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
