package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
}
