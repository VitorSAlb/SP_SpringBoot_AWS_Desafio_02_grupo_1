package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.repositories.projection.SubjectProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    @Query("SELECT s FROM Subject s WHERE s.name = :name")
    Subject findByName(String name);

    @Query("SELECT s FROM Subject s WHERE s.name = :subjectName AND s.course.name = :courseName")
    Optional<Subject> findByNameAndCourseName(@Param("subjectName") String subjectName, @Param("courseName") String courseName);

    List<Subject> findByMainProfessor(Professor professor);
    List<Subject> findBySubstituteProfessor(Professor professor);


    boolean existsByName(String name);
}
