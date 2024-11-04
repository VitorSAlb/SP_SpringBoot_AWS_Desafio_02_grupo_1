package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Professor mainProfessor;
    private Professor substituteProfessor;
    private Course course;

    @BeforeEach
    public void setup() {
        subjectRepository.deleteAll();
        professorRepository.deleteAll();
        courseRepository.deleteAll();

        mainProfessor = new Professor();
        mainProfessor.setEmail("jose.silva@university.com");
        mainProfessor.setFirstName("José");
        mainProfessor.setLastName("Silva");
        mainProfessor.setPassword("password123");
        mainProfessor.setBirthdate(LocalDate.of(1970, 5, 15));
        mainProfessor.setRole(Role.ROLE_PROFESSOR);
        professorRepository.save(mainProfessor);

        substituteProfessor = new Professor();
        substituteProfessor.setEmail("ana.maria@university.com");
        substituteProfessor.setFirstName("Ana");
        substituteProfessor.setLastName("Maria");
        substituteProfessor.setPassword("password456");
        substituteProfessor.setBirthdate(LocalDate.of(1975, 3, 10));
        substituteProfessor.setRole(Role.ROLE_PROFESSOR);
        professorRepository.save(substituteProfessor);

        course = new Course();
        course.setName("Software Engineering");
        course.setDescription("Bachelor in Software Engineering");
        courseRepository.save(course);
    }

    @Test
    public void testFindByName() {
        Subject subject = new Subject();
        subject.setName("Object-Oriented Programming");
        subject.setDescription("Introduction to OOP");
        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);
        subject.setCourse(course);
        subjectRepository.save(subject);

        Subject found = subjectRepository.findByName("Object-Oriented Programming");
        assertNotNull(found);
        assertEquals("Object-Oriented Programming", found.getName());
    }

    @Test
    public void testFindByNameAndCourseName() {
        Subject subject = new Subject();
        subject.setName("Data Structures");
        subject.setDescription("Introduction to Data Structures");
        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);
        subject.setCourse(course);
        subjectRepository.save(subject);

        var found = subjectRepository.findByNameAndCourseName("Data Structures", "Software Engineering");
        assertTrue(found.isPresent());
        assertEquals("Data Structures", found.get().getName());
    }

    @Test
    public void testFindByMainProfessor() {
        Subject subject = new Subject();
        subject.setName("Operating Systems");
        subject.setDescription("Introduction to Operating Systems");
        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);
        subject.setCourse(course);
        subjectRepository.save(subject);

        List<Subject> subjects = subjectRepository.findByMainProfessor(mainProfessor);
        assertFalse(subjects.isEmpty());
        assertEquals(1, subjects.size());
        assertEquals("Operating Systems", subjects.get(0).getName());
    }

    @Test
    public void testFindBySubstituteProfessor() {
        Subject subject = new Subject();
        subject.setName("Database Systems");
        subject.setDescription("Introduction to Database Systems");
        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);
        subject.setCourse(course);
        subjectRepository.save(subject);

        List<Subject> subjects = subjectRepository.findBySubstituteProfessor(substituteProfessor);
        assertFalse(subjects.isEmpty());
        assertEquals(1, subjects.size());
        assertEquals("Database Systems", subjects.get(0).getName());
    }

    @Test
    public void testExistsByName_Positive() {
        Subject subject = new Subject();
        subject.setName("Machine Learning");
        subject.setDescription("Introduction to Machine Learning");
        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);
        subject.setCourse(course);
        subjectRepository.save(subject);

        boolean exists = subjectRepository.existsByName("Machine Learning");
        assertTrue(exists);
    }

    @Test
    public void testExistsByName_Negative() {
        boolean exists = subjectRepository.existsByName("Artificial Intelligence");
        assertFalse(exists);
    }
}
