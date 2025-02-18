package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.repositories.projection.ProfessorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    @Query("select p from Professor p")
    Page<ProfessorProjection> findAllP(Pageable pageable);

    @Query("SELECT p FROM Professor p WHERE p.email = :email")
    Professor findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
}
