package com.compass.desafio02.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.services.StudentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/planets")
public class StudentController {
   
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Student>createStudent(@RequestBody Student student) {
        Student studentCreated = studentService.createStudent(student);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(studentCreated);
    }
    
}
