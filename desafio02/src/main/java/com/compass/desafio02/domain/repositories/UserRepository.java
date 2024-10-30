package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
