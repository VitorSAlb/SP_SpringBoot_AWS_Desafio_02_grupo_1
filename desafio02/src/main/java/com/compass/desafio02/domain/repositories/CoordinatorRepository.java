package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Integer> {

    @Query("SELECT c FROM Coordinator c WHERE c.email = :email")
    Student findByEmail(@Param("email") String email);
}
