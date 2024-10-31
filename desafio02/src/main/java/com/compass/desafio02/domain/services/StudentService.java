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
        if (!isPasswordValid(student.getPassword())) {
            throw new IllegalArgumentException("The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.");
        }

        return studentRepository.save(student);
    }

    public Student findById(Integer id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Student not founded") // TROCAR EXCESSÃO
        );
    }

    public Student findByEmail(String email) {
        try {
            return studentRepository.findByEmail(email);
        } catch (RuntimeException e) {
            throw new RuntimeException("Email not founded"); // TROCAR EXCESSÃO
        }

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

    public void editPassword(Integer id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirmation password do not match.");
        }

        if (!isPasswordValid(newPassword)) {
            throw new IllegalArgumentException("Password does not meet security requirements.");
        }

        Student student = findById(id);

        if (!Objects.equals(student.getPassword(), currentPassword)) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        student.setPassword(newPassword);
        studentRepository.save(student);
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");
    }
}
