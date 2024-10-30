package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.StudentRepository;

import java.util.Objects;

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

    public Page<StudentProjection> findAll(Pageable pageable) {
        return studentRepository.findAllP(pageable);
    }

    public Student update(Integer id, Student newStudent) {
        Student existingStudent = findById(id);

        existingStudent.setEmail(newStudent.getEmail());
        existingStudent.setFirstName(newStudent.getFirstName());
        existingStudent.setLastName(newStudent.getLastName());
        existingStudent.setAddress(newStudent.getAddress());
        existingStudent.setBirthdate(newStudent.getBirthdate());

        return studentRepository.save(existingStudent);
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }


    // COLOCAR O ENCRIPTADOR DE SENHA
    public Student editPassword(Integer id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException(); // TROCAR EXCESSÃO
        }

        Student student = findById(id);

        if (!Objects.equals(student.getPassword(), currentPassword)) {
            throw new RuntimeException(); // TROCAR EXCESSÃO
        }
        student.setPassword(newPassword);
        studentRepository.save(student);
        return student;
    }
}
