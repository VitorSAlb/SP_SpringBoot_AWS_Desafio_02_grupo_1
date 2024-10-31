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

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Query("SELECT s FROM Subject s")
    Page<SubjectProjection> findAllP(Pageable pageable);

    @Query("SELECT s FROM Subject s WHERE s.mainProfessor = :professor")
    List<Subject> findByMainProfessor(@Param("professor") Professor professor);

    @Query("SELECT s FROM Subject s WHERE s.substituteProfessor = :professor")
    List<Subject> findBySubstituteProfessor(@Param("professor") Professor professor);

    @Query("SELECT COUNT(s) > 0 FROM Subject s WHERE s.name = :name")
    boolean existsByName(@Param("name") String name);
}
