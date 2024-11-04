package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Enrollment;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.domain.repositories.EnrollmentRepository;
import com.compass.desafio02.domain.repositories.StudentRepository;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEnrollment_Success() {
        // Dados de exemplo
        Student student = new Student();
        student.setId(1);
        student.setBirthdate(LocalDate.of(2000, 1, 1));  // Aluno maior de idade
        student.setEmail("student@test.com");

        Course course = new Course();
        course.setId(1);
        course.setName("Course Name");

        Enrollment enrollment = new Enrollment(student, course);

        // Configurações de mock
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentId(student.getId())).thenReturn(false);
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(false);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        // Ação
        Enrollment createdEnrollment = enrollmentService.createEnrollment(course.getId(), student.getId());

        // Verificações
        assertNotNull(createdEnrollment);
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
        verify(courseService, times(1)).addStudentToCourse(course.getName(), student.getEmail());
    }

    @Test
    void testCreateEnrollment_StudentNotFound() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                enrollmentService.createEnrollment(1, 1));

        assertEquals("Student not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreateEnrollment_CourseNotFound() {
        Student student = new Student();
        student.setId(1);
        student.setBirthdate(LocalDate.of(2000, 1, 1));  // Aluno maior de idade
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                enrollmentService.createEnrollment(1, 1));

        assertEquals("Course not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreateEnrollment_StudentUnderage() {
        Student student = new Student();
        student.setId(1);
        student.setBirthdate(LocalDate.of(2010, 1, 1));  // Aluno menor de idade
        Course course = new Course();
        course.setId(1);

        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                enrollmentService.createEnrollment(course.getId(), student.getId()));

        assertEquals("Student must be at least 18 years old to enroll.", exception.getMessage());
    }

    @Test
    void testCreateEnrollment_AlreadyEnrolledInAnotherCourse() {
        Student student = new Student();
        student.setId(1);
        student.setBirthdate(LocalDate.of(2000, 1, 1));  // Aluno maior de idade
        Course course = new Course();
        course.setId(1);

        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentId(student.getId())).thenReturn(true);  // Já matriculado em outro curso

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                enrollmentService.createEnrollment(course.getId(), student.getId()));

        assertEquals("Student is already enrolled in another course.", exception.getMessage());
    }

    @Test
    void testCreateEnrollment_AlreadyEnrolledInSameCourse() {
        Student student = new Student();
        student.setId(1);
        student.setBirthdate(LocalDate.of(2000, 1, 1));  // Aluno maior de idade
        Course course = new Course();
        course.setId(1);

        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true);  // Já matriculado no mesmo curso

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                enrollmentService.createEnrollment(course.getId(), student.getId()));

        assertEquals("Student is already enrolled in this course.", exception.getMessage());
    }
}
