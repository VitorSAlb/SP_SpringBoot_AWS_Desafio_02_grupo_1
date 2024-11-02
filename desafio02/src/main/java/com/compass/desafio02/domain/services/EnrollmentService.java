package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Enrollment;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.EnrollmentRepository;
import com.compass.desafio02.domain.repositories.StudentRepository;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    public Page<Enrollment> getAllEnrollments(Pageable pageable) {
        return enrollmentRepository.findAll(pageable);
    }

    public Enrollment findEnrollmentById(Integer id) {
        return enrollmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
    }

    @Transactional
    public Enrollment createEnrollment(Integer courseId, Integer studentId) {
        // Verifica se o aluno existe
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        // Verifica se o curso existe
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        // Verificar se o aluno tem 18 anos ou mais
        if (!isStudentOfAge(student)) {
            throw new IllegalArgumentException("Student must be at least 18 years old to enroll.");
        }

        // Verificar se o aluno está matriculado em outro curso
        if (enrollmentRepository.existsByStudentId(student.getId())) {
            throw new IllegalArgumentException("Student is already enrolled in another course.");
        }

        // Verificar se o aluno já está matriculado neste curso
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("Student is already enrolled in this course.");
        }

        // Criar a nova matrícula
        Enrollment enrollment = new Enrollment(student, course);
        enrollmentRepository.save(enrollment);

        // Adicionar as matérias do curso ao estudante
        course.getSubjects().forEach(student::addSubject);
        studentRepository.save(student);

        return enrollment;
    }

    public void deleteEnrollment(Integer id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Enrollment not found with id: " + id);
        }
        enrollmentRepository.deleteById(id);
    }

    public List<Enrollment> getEnrollmentsByCourseId(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));
        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<Enrollment> getEnrollmentsByStudentId(Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        return enrollmentRepository.findByStudentId(studentId);
    }

    private boolean isStudentOfAge(Student student) {
        return Period.between(student.getBirthdate(), LocalDate.now()).getYears() >= 18;
    }

}

