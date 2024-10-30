package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
