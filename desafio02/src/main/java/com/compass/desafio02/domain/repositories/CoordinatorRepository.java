package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Integer> {
}
