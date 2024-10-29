package com.compass.desafio02.domain.services;

import org.springframework.stereotype.Service;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.StudentRepository;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        return StudentRepository.save(student);

    }
}
