package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
