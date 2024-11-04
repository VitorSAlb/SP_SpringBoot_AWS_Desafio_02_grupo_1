package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
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
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setup() {
        studentRepository.deleteAll();
    }

    @Test
    public void testFindAllP() {
        Student student1 = new Student();
        student1.setEmail("student1@university.com");
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setPassword("password1");
        student1.setBirthdate(LocalDate.of(2000, 1, 1));
        student1.setRole(Role.ROLE_STUDENT);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setEmail("student2@university.com");
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setPassword("password2");
        student2.setBirthdate(LocalDate.of(2001, 2, 2));
        student2.setRole(Role.ROLE_STUDENT);
        studentRepository.save(student2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<StudentProjection> page = studentRepository.findAllP(pageable);

        assertNotNull(page);
        List<StudentProjection> students = page.getContent();
        assertEquals(2, students.size());
    }

    @Test
    public void testFindByEmail() {
        Student student = new Student();
        student.setEmail("test@university.com");
        student.setFirstName("Test");
        student.setLastName("User");
        student.setPassword("password");
        student.setBirthdate(LocalDate.of(1999, 3, 3));
        student.setRole(Role.ROLE_STUDENT);
        studentRepository.save(student);

        Student found = studentRepository.findByEmail("test@university.com");

        assertNotNull(found);
        assertEquals("test@university.com", found.getEmail());
    }

    @Test
    public void testExistsByEmail_Positive() {
        Student student = new Student();
        student.setEmail("exists@university.com");
        student.setFirstName("Exists");
        student.setLastName("User");
        student.setPassword("password");
        student.setBirthdate(LocalDate.of(1998, 4, 4));
        student.setRole(Role.ROLE_STUDENT);
        studentRepository.save(student);

        boolean exists = studentRepository.existsByEmail("exists@university.com");

        assertTrue(exists);
    }

    @Test
    public void testExistsByEmail_Negative() {
        boolean exists = studentRepository.existsByEmail("nonexistent@university.com");

        assertFalse(exists);
    }
}
