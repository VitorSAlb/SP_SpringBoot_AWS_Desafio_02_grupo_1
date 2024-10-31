package com.compass.desafio02.util.config;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.domain.repositories.ProfessorRepository;
import com.compass.desafio02.domain.repositories.StudentRepository;
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
    private ProfessorRepository professorRepository;

    @Override
    public void run(String... args) throws Exception {

        studentRepository.deleteAll();
        courseRepository.deleteAll();
        coordinatorRepository.deleteAll();
        professorRepository.deleteAll();


        Student s1 = new Student("s01", "s01", "s1@email.com", LocalDate.now(), "123456", "test1 address");
        Student s2 = new Student("s02", "s02", "s2@email.com", LocalDate.now(), "123456", "test2 address");
        Student s3 = new Student("s03", "s03", "s3@email.com", LocalDate.now(), "123456", "test3 address");


        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);



        Coordinator coo1 = new Coordinator("coo1", "coo1", "coo1@email.com", LocalDate.now(), "123456", null);
        Coordinator coo2 = new Coordinator("coo2", "coo2", "coo2@email.com", LocalDate.now(), "123456", null);
        Coordinator coo3 = new Coordinator("coo3", "coo3", "coo3@email.com", LocalDate.now(), "123456", null);

        coordinatorRepository.save(coo1);
        coordinatorRepository.save(coo2);
        coordinatorRepository.save(coo3);



        Course c1 = new Course("c1","c1 description", coo1);
        Course c2 = new Course("c2","c1 description", coo2);
        Course c3 = new Course("c3","c1 description", coo3);

        courseRepository.save(c1);
        courseRepository.save(c2);
        courseRepository.save(c3);

        Professor p1 = new Professor("prof1", "prof1LastName", "prof1@email.com", LocalDate.now(), "123456");
        Professor p2 = new Professor("prof2", "prof2LastName", "prof2@email.com", LocalDate.now(), "123456");

        professorRepository.saveAll(Arrays.asList(p1, p2));

    }
}
