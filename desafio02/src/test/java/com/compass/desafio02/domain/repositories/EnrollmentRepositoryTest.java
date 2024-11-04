package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Enrollment;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class EnrollmentRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;
    private Course course;

    @BeforeEach
    public void setup() {
        enrollmentRepository.deleteAll();
        courseRepository.deleteAll();
        studentRepository.deleteAll();

        student = new Student();
        student.setEmail("student@example.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setPassword("password123");
        student.setBirthdate(LocalDate.of(2000, 1, 1));
        student.setRole(Role.ROLE_STUDENT);
        studentRepository.save(student);

        course = new Course();
        course.setName("Computer Science");
        course.setDescription("Bachelor of Science in Computer Science");
        courseRepository.save(course);
    }

    @Test
    public void testExistsByStudentAndCourse() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);

        boolean exists = enrollmentRepository.existsByStudentAndCourse(student, course);
        assertTrue(exists);
    }

    @Test
    public void testExistsByStudentId_Positive() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);

        boolean exists = enrollmentRepository.existsByStudentId(student.getId());
        assertTrue(exists);
    }

    @Test
    public void testExistsByStudentId_Negative() {
        boolean exists = enrollmentRepository.existsByStudentId(999);
        assertFalse(exists);
    }

    @Test
    public void testFindByCourseId() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);

        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(course.getId());
        assertFalse(enrollments.isEmpty());
        assertEquals(1, enrollments.size());
    }

    @Test
    public void testFindByStudentId() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());
        assertFalse(enrollments.isEmpty());
        assertEquals(1, enrollments.size());
    }
}
