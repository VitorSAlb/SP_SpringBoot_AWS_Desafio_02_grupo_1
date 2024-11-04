package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT s FROM User s WHERE s.email = :email")
    User findByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email LIKE :email")
    Optional<User> findByEmailUser(@Param("email") String email);
}
