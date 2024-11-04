package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.User;
import com.compass.desafio02.domain.entities.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setRole(Role.ROLE_STUDENT);
        entityManager.persistAndFlush(user);
    }

    @Test
    void shouldFindUserByEmail_Positive() {
        Optional<User> foundUser = userRepository.findByEmailUser("john.doe@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(foundUser.get().getFirstName()).isEqualTo("John");
        assertThat(foundUser.get().getLastName()).isEqualTo("Doe");
        assertThat(foundUser.get().getRole()).isEqualTo(Role.ROLE_STUDENT);
    }

    @Test
    void shouldNotFindUserByEmail_WhenEmailDoesNotExist() {
        Optional<User> foundUser = userRepository.findByEmailUser("nonexistent@example.com");

        assertThat(foundUser).isNotPresent();
    }
}
