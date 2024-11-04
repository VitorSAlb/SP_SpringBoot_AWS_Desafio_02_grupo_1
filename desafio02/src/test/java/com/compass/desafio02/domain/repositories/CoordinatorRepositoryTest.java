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
        coordinator1.setEmail("maria.santos@university.com");
        coordinator1.setFirstName("Maria");
        coordinator1.setLastName("Santos");
        coordinator1.setPassword("password123");
        coordinator1.setBirthdate(LocalDate.of(1975, 5, 20));
        coordinator1.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator1);

        Coordinator coordinator2 = new Coordinator();
        coordinator2.setEmail("joao.lima@university.com");
        coordinator2.setFirstName("João");
        coordinator2.setLastName("Lima");
        coordinator2.setPassword("password456");
        coordinator2.setBirthdate(LocalDate.of(1980, 8, 15));
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
        coordinator.setEmail("camila.souza@university.com");
        coordinator.setFirstName("Camila");
        coordinator.setLastName("Souza");
        coordinator.setPassword("securePass123");
        coordinator.setBirthdate(LocalDate.of(1992, 11, 10));
        coordinator.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator);

        Coordinator found = coordinatorRepository.findByEmail("camila.souza@university.com");

        assertNotNull(found);
        assertEquals("camila.souza@university.com", found.getEmail());
    }

    @Test
    public void testExistsByEmail_Positive() {
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("paulo.silva@university.com");
        coordinator.setFirstName("Paulo");
        coordinator.setLastName("Silva");
        coordinator.setPassword("myPassword789");
        coordinator.setBirthdate(LocalDate.of(1985, 6, 30));
        coordinator.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator);

        boolean exists = coordinatorRepository.existsByEmail("paulo.silva@university.com");

        assertTrue(exists);
    }

    @Test
    public void testExistsByEmail_Negative() {
        boolean exists = coordinatorRepository.existsByEmail("ana.oliveira@university.com");

        assertFalse(exists);
    }
}
