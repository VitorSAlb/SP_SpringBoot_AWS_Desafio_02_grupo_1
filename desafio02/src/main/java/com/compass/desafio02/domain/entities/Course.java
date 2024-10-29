package com.compass.desafio02.domain.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tb_course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "coordinator_id", referencedColumnName = "id", nullable = false, unique = true)
    private Coordinator coordinator;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Subject> subject = new ArrayList<>();


    public Course() {
    }

    public Course(String name, String description, Coordinator coordinator) {
        this.name = name;
        this.description = description;
        this.coordinator = coordinator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    public List<Subject> getSubject() {
        return subject;
    }

    public void setSubject(List<Subject> subject) {
        this.subject = subject;
    }

    // Método para adicionar uma disciplina - duvida
    public void addSubject(Subject subject) {
        this.subject.add(subject);
        subject.setCourse(this);
    }

    // Método para remover uma disciplina - duvida
    public void removeSubject(Subject subject) {
        this.subject.remove(subject);
        subject.setCourse(null);
    }


}
