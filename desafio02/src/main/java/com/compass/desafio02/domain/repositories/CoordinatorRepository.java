package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.repositories.projection.CoordinatorProjection;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Integer> {

    @Query("select c from Coordinator c")
    Page<CoordinatorProjection> findAllP(Pageable pageable);

    @Query("SELECT c FROM Coordinator c WHERE c.email = :email")
    Coordinator findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
}
