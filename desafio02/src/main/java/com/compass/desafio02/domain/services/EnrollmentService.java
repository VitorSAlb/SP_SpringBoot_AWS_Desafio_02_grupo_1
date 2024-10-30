package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Enrollment;
import com.compass.desafio02.domain.repositories.EnrollmentRepository;
import com.compass.desafio02.domain.repositories.StudentRepository;
import com.compass.desafio02.domain.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
}
