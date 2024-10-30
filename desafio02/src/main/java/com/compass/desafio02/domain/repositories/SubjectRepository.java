package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
