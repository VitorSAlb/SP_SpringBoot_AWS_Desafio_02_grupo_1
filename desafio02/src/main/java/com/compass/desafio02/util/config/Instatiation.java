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

        subjectRepository.deleteAll();
        enrollmentRepository.deleteAll();
//        studentRepository.deleteAll();
//        courseRepository.deleteAll();
//        coordinatorRepository.deleteAll();
//        studentRepository.deleteAll();
//        professorRepository.deleteAll();
//
//        Professor p1 = new Professor("P1", "P1", "p1@email.com", LocalDate.now(), "123456");
//        Professor p2 = new Professor("P2", "P2", "p2@email.com", LocalDate.now(), "123456");
//        Professor p3 = new Professor("P3", "P3", "p3@email.com", LocalDate.now(), "123456");
//
//        professorRepository.saveAll(Arrays.asList(p1, p2, p3));
//
//
//        Student s1 = new Student("s01", "s01", "s1@email.com", LocalDate.parse("2000-01-01"), "@Ab12345", "test1 address");
//        Student s2 = new Student("s02", "s02", "s2@email.com", LocalDate.now(), "@Ab12345", "test2 address");
//        Student s3 = new Student("s03", "s03", "s3@email.com", LocalDate.now(), "@Ab12345", "test3 address");
//
//
//        studentRepository.saveAll(Arrays.asList(s1, s2, s3));
//
//        Coordinator coo1 = new Coordinator("coo1", "coo1", "coo1@email.com", LocalDate.now(), "123456", null);
//        Coordinator coo2 = new Coordinator("coo2", "coo2", "coo2@email.com", LocalDate.now(), "123456", null);
//        Coordinator coo3 = new Coordinator("coo3", "coo3", "coo3@email.com", LocalDate.now(), "123456", null);
//
//        coordinatorRepository.saveAll(Arrays.asList(coo1, coo2, coo3));
//
//        Subject sub1 = new Subject("Math", "Basic Mathematics", p1, null, null);
//        Subject sub2 = new Subject("Science", "Introduction to Science", p2, null, null);
//        Subject sub3 = new Subject("History", "World History", p3, null, null);
//
//
//        Course c1 = new Course("c1","c1 description", coo1);
//        Course c2 = new Course("c2","c1 description", coo2);
//        Course c3 = new Course("c3","c1 description", coo3);
//
//        c1.addSubject(sub1);
//        c1.addSubject(sub2);
//        c2.addSubject(sub3);
//
//        courseRepository.saveAll(Arrays.asList(c1, c2, c3));
//        subjectRepository.saveAll(Arrays.asList(sub1, sub2, sub3));

    }
}