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

        Student s1 = new Student("test01", "test01", "test1@email.com", LocalDate.now(), "123456", "test1 address");
        Student s2 = new Student("test02", "test02", "test2@email.com", LocalDate.now(), "123456", "test2 address");
        Student s3 = new Student("test03", "test03", "test3@email.com", LocalDate.now(), "123456", "test3 address");


        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);

    }
}
