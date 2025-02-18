package com.compass.desafio02.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
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

    @JsonBackReference
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinator_id", referencedColumnName = "id")
    private Coordinator coordinator;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Subject> subjects = new ArrayList<>();

    @OneToMany
    private List<Student> students = new ArrayList<>();


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
        if (coordinator != null) {
            if (coordinator.getCourse() != this) {
                coordinator.setCourse(this);
            }
        }
    }


    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject subjects) {
        this.subjects.add(subjects);
        subjects.setCourse(this);
    }

    public void removeSubject(Subject subjects) {
        this.subjects.remove(subjects);
        subjects.setCourse(null);
    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            this.students.add(student);
            student.setCourse(this);
        }
    }


    public void removeStudent(Student student) {
        if (students.remove(student)) {
            student.setCourse(null);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", coordinator=" + coordinator.getEmail() +
                ", subjects=" + subjects +
                ", students=" + students +
                '}';
    }
}
