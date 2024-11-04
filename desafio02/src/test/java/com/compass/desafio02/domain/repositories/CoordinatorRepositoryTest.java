package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.CoordinatorProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class CoordinatorRepositoryTest {

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @BeforeEach
    public void setup() {
        coordinatorRepository.deleteAll();
    }

    @Test
    public void testFindAllP() {
        Coordinator coordinator1 = new Coordinator();
        coordinator1.setEmail("coordinator1@university.com");
        coordinator1.setFirstName("Alice");
        coordinator1.setLastName("Smith");
        coordinator1.setPassword("password1");
        coordinator1.setBirthdate(LocalDate.of(1980, 1, 1));
        coordinator1.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator1);

        Coordinator coordinator2 = new Coordinator();
        coordinator2.setEmail("coordinator2@university.com");
        coordinator2.setFirstName("Bob");
        coordinator2.setLastName("Johnson");
        coordinator2.setPassword("password2");
        coordinator2.setBirthdate(LocalDate.of(1985, 2, 2));
        coordinator2.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<CoordinatorProjection> page = coordinatorRepository.findAllP(pageable);

        assertNotNull(page);
        List<CoordinatorProjection> coordinators = page.getContent();
        assertEquals(2, coordinators.size());
    }

    @Test
    public void testFindByEmail() {
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("test@university.com");
        coordinator.setFirstName("Test");
        coordinator.setLastName("User");
        coordinator.setPassword("password");
        coordinator.setBirthdate(LocalDate.of(1990, 3, 3));
        coordinator.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator);

        Coordinator found = coordinatorRepository.findByEmail("test@university.com");

        assertNotNull(found);
        assertEquals("test@university.com", found.getEmail());
    }

    @Test
    public void testExistsByEmail_Positive() {
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("exists@university.com");
        coordinator.setFirstName("Exists");
        coordinator.setLastName("User");
        coordinator.setPassword("password");
        coordinator.setBirthdate(LocalDate.of(1995, 4, 4));
        coordinator.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator);

        boolean exists = coordinatorRepository.existsByEmail("exists@university.com");

        assertTrue(exists);
    }

    @Test
    public void testExistsByEmail_Negative() {
        boolean exists = coordinatorRepository.existsByEmail("nonexistent@university.com");

        assertFalse(exists);
    }
}
