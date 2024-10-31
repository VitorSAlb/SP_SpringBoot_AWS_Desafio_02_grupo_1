package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("SELECT n FROM Course n WHERE n.name = :name")
    Course findByName(@Param("name") String name);

    @Query("select c from Course c")
    Page<Course> findAllP(Pageable pageable);
}
