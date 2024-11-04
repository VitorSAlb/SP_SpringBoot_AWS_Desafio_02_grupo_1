package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Enrollment;
import com.compass.desafio02.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    boolean existsByStudentAndCourse(Student student, Course course);

    boolean existsByStudentId(Integer studentId);

    List<Enrollment> findByCourseId(Integer courseId);

    List<Enrollment> findByStudentId(Integer studentId);
}
