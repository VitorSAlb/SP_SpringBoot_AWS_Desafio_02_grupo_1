package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.ProfessorProjection;
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
public class ProfessorRepositoryTest {

    @Autowired
    private ProfessorRepository professorRepository;

    @BeforeEach
    public void setup() {
        professorRepository.deleteAll();
    }

    @Test
    public void testFindAllP() {
        Professor professor1 = new Professor();
        professor1.setEmail("professor1@university.com");
        professor1.setFirstName("John");
        professor1.setLastName("Doe");
        professor1.setPassword("password1");
        professor1.setBirthdate(LocalDate.of(1980, 1, 1));
        professor1.setRole(Role.ROLE_PROFESSOR);
        professorRepository.save(professor1);

        Professor professor2 = new Professor();
        professor2.setEmail("professor2@university.com");
        professor2.setFirstName("Jane");
        professor2.setLastName("Smith");
        professor2.setPassword("password2");
        professor2.setBirthdate(LocalDate.of(1985, 2, 2));
        professor2.setRole(Role.ROLE_PROFESSOR);
        professorRepository.save(professor2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<ProfessorProjection> page = professorRepository.findAllP(pageable);

        assertNotNull(page);
        List<ProfessorProjection> professors = page.getContent();
        assertEquals(2, professors.size());
    }

    @Test
    public void testFindByEmail() {
        Professor professor = new Professor();
        professor.setEmail("test@university.com");
        professor.setFirstName("Test");
        professor.setLastName("User");
        professor.setPassword("password");
        professor.setBirthdate(LocalDate.of(1990, 3, 3));
        professor.setRole(Role.ROLE_PROFESSOR);
        professorRepository.save(professor);

        Professor found = professorRepository.findByEmail("test@university.com");

        assertNotNull(found);
        assertEquals("test@university.com", found.getEmail());
    }

    @Test
    public void testExistsByEmail_Positive() {
        Professor professor = new Professor();
        professor.setEmail("exists@university.com");
        professor.setFirstName("Exists");
        professor.setLastName("User");
        professor.setPassword("password");
        professor.setBirthdate(LocalDate.of(1995, 4, 4));
        professor.setRole(Role.ROLE_PROFESSOR);
        professorRepository.save(professor);

        boolean exists = professorRepository.existsByEmail("exists@university.com");

        assertTrue(exists);
    }

    @Test
    public void testExistsByEmail_Negative() {
        boolean exists = professorRepository.existsByEmail("nonexistent@university.com");

        assertFalse(exists);
    }
}
