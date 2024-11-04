package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import com.compass.desafio02.infrastructure.exceptions.user.*;
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
            throw new UserCreationException("The password does not meet security requirements.");
        }

        try {
            return studentRepository.save(student);
        } catch (Exception e) {
            throw new UserCreationException("Error saving student: " + e.getMessage());
        }
    }

    public Student findById(Integer id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Student not found with ID: " + id)
        );
    }

    public Student findByEmail(String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new UserNotFoundException("Student not found with email: " + email);
        }
        return student;
    }

    public Page<StudentProjection> findAll(Pageable pageable) {
        return studentRepository.findAllP(pageable);
    }

    public Student update(String email, Student newStudent) {
        Student existingStudent = findByEmail(email);
        try {
            existingStudent.setEmail(newStudent.getEmail());
            existingStudent.setFirstName(newStudent.getFirstName());
            existingStudent.setLastName(newStudent.getLastName());
            existingStudent.setAddress(newStudent.getAddress());
            existingStudent.setBirthdate(newStudent.getBirthdate());
            return studentRepository.save(existingStudent);
        } catch (Exception e) {
            throw new UserUpdateException("Error updating student: " + e.getMessage());
        }
    }

    public void delete(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new UserDeletionException("Cannot delete student: Student not found with ID: " + id);
        }

        try {
            studentRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserDeletionException("Error deleting student: " + e.getMessage());
        }
    }

    public void delete(String email) {
        Student student = findByEmail(email);

        if (!studentRepository.existsById(student.getId())) {
            throw new UserDeletionException("Cannot delete student: Student not found with Email: " + email);
        }

        if (student.getCourse() != null) {
            throw new UserDeletionException("Error Student is linked to a course, remove this student of course to delete with successfully");
        }

        try {
            studentRepository.deleteById(student.getId());
        } catch (Exception e) {
            throw new UserDeletionException("Error deleting student: " + e.getMessage());
        }
    }


    public void editPassword(String email, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordUpdateException("New password and confirmation password do not match.");
        }

        if (!isPasswordValid(newPassword)) {
            throw new PasswordUpdateException("The password does not meet security requirements.");
        }

        Student student = findByEmail(email);

        if (!Objects.equals(student.getPassword(), currentPassword)) {
            throw new PasswordUpdateException("Current password is incorrect.");
        }

        student.setPassword(newPassword);
        try {
            studentRepository.save(student);
        } catch (Exception e) {
            throw new PasswordUpdateException("Error updating password: " + e.getMessage());
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");
    }

    public void addCourse(Course course, Student student) {
        student.setCourse(course);
        studentRepository.save(student);
    }
}
