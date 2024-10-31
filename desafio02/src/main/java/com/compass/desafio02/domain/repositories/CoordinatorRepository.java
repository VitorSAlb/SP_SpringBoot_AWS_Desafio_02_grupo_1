package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Integer> {

    @Query("SELECT c FROM Coordinator c WHERE c.email = :email")
    Coordinator findByEmail(@Param("email") String email);
}
