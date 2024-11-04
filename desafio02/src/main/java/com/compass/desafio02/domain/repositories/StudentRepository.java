package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("select s from Student s")
    Page<StudentProjection> findAllP(Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.email = :email")
    Student findByEmail(@Param("email") String email);


    boolean existsByEmail(String email);
}
