package com.compass.desafio02.util.config;

import com.compass.desafio02.domain.entities.Student;
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

    @Override
    public void run(String... args) throws Exception {

        studentRepository.deleteAll();

        Student s1 = new Student("first", "second", "test@email.com", LocalDate.now(), "Random Address");
        Student s2 = new Student("first2", "second2", "test2@email.com", LocalDate.now(), "Random Address");
        Student s3 = new Student("first3", "second3", "test3@email.com", LocalDate.now(), "Random Address");

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);

    }
}
