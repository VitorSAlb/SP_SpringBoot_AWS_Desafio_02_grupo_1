package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
