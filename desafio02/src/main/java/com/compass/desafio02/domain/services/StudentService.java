package com.compass.desafio02.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student findById(Integer id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Student not founded") // TROCAR EXCESSÃO
        );
    }

    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Student update(Integer id, Student newStudent) {
        Student existingStudent = findById(id);

        existingStudent.setEmail(newStudent.getEmail()); // TESMOS QUE VERIFICAR SE O NOVO EMAIL JÁ EXISTE PARA PODER EDITAR
        existingStudent.setFirstName(newStudent.getFirstName());
        existingStudent.setLastName(newStudent.getLastName());
        existingStudent.setAddress(newStudent.getAddress());
        existingStudent.setBirthdate(newStudent.getBirthdate());

        return studentRepository.save(existingStudent);
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
}
