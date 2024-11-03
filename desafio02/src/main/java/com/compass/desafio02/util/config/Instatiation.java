package com.compass.desafio02.util.config;

import com.compass.desafio02.domain.entities.*;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.domain.repositories.StudentRepository;
import com.compass.desafio02.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class Instatiation implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public void run(String... args) throws Exception {

        // Primeiro, deletamos todas as entidades com ordem de dependência correta
        enrollmentRepository.deleteAll(); // Exclusão de matrículas (muitos para muitos)
        subjectRepository.deleteAll();    // Exclusão de matérias
//        studentRepository.deleteAll();    // Exclusão de estudantes
//        courseRepository.deleteAll();     // Exclusão de cursos?
//        professorRepository.deleteAll();  // Exclusão de professores
//        coordinatorRepository.deleteAll(); // Exclusão de coordenadores


    }
}