package com.compass.desafio02.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_subject")
public class Subject{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "main_professor_id", nullable = false)
    @JsonManagedReference
    private Professor mainProfessor;

    @ManyToOne
    @JoinColumn(name = "substitute_professor_id")
    private Professor substituteProfessor;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

    public Subject() {
    }

    public Subject(String name, String description, Professor mainProfessor, Professor substituteProfessor, Course course) {
        this.name = name;
        this.description = description;
        this.mainProfessor = mainProfessor;
        this.substituteProfessor = substituteProfessor;
        this.course = course;
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

    public Professor getMainProfessor() {
        return mainProfessor;
    }

    public void setMainProfessor(Professor mainProfessor) {
        this.mainProfessor = mainProfessor;
    }

    public Professor getSubstituteProfessor() {
        return substituteProfessor;
    }

    public void setSubstituteProfessor(Professor substituteProfessor) {
        this.substituteProfessor = substituteProfessor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    // Método para adicionar um aluno - Duvida se mantem ou não para o futuro
    public void addStudent(Student student) {
        if (!students.contains(student)) {
            this.students.add(student);
            student.getSubjects().add(this);
        }
    }

    // Método para remover um aluno - Duvida se mantem ou não para o futuro
    public void removeStudent(Student student) {
        if (students.remove(student)) {
            student.getSubjects().remove(this);
        }
    }

}
