package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("select c from Course c")
    Page<Course> findAllP(Pageable pageable);
}
