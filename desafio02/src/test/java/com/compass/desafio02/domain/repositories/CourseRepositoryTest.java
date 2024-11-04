package com.compass.desafio02.domain.repositories;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.enums.Role;
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
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @BeforeEach
    public void setup() {
        courseRepository.deleteAll();
        coordinatorRepository.deleteAll();
    }

    @Test
    public void testFindAllP() {
        Coordinator coordinator1 = new Coordinator();
        coordinator1.setEmail("julia.morais@university.com");
        coordinator1.setFirstName("Julia");
        coordinator1.setLastName("Morais");
        coordinator1.setPassword("password789");
        coordinator1.setBirthdate(LocalDate.of(1980, 9, 15));
        coordinator1.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator1);

        Coordinator coordinator2 = new Coordinator();
        coordinator2.setEmail("marcelo.alves@university.com");
        coordinator2.setFirstName("Marcelo");
        coordinator2.setLastName("Alves");
        coordinator2.setPassword("password123");
        coordinator2.setBirthdate(LocalDate.of(1985, 5, 10));
        coordinator2.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator2);


        Course course1 = new Course();
        course1.setName("Computer Science");
        course1.setDescription("Bachelor in Computer Science");
        course1.setCoordinator(coordinator1);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setName("Information Systems");
        course2.setDescription("Bachelor in Information Systems");
        course2.setCoordinator(coordinator2);
        courseRepository.save(course2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> page = courseRepository.findAll(pageable);

        assertNotNull(page);
        List<Course> courses = page.getContent();
        assertEquals(2, courses.size());
    }



    @Test
    public void testFindByName() {
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("fabio.santos@university.com");
        coordinator.setFirstName("Fabio");
        coordinator.setLastName("Santos");
        coordinator.setPassword("password321");
        coordinator.setBirthdate(LocalDate.of(1978, 6, 25));
        coordinator.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator);

        Course course = new Course();
        course.setName("Data Science");
        course.setDescription("Bachelor in Data Science");
        course.setCoordinator(coordinator);
        courseRepository.save(course);

        Course found = courseRepository.findByName("Data Science");

        assertNotNull(found);
        assertEquals("Data Science", found.getName());
    }

    @Test
    public void testExistsByName_Positive() {
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("renata.oliveira@university.com");
        coordinator.setFirstName("Renata");
        coordinator.setLastName("Oliveira");
        coordinator.setPassword("myPassword789");
        coordinator.setBirthdate(LocalDate.of(1982, 4, 12));
        coordinator.setRole(Role.ROLE_COORDINATOR);
        coordinatorRepository.save(coordinator);

        Course course = new Course();
        course.setName("Software Engineering");
        course.setDescription("Bachelor in Software Engineering");
        course.setCoordinator(coordinator);
        courseRepository.save(course);

        boolean exists = courseRepository.existsByName("Software Engineering");

        assertTrue(exists);
    }

    @Test
    public void testExistsByName_Negative() {
        boolean exists = courseRepository.existsByName("Mechanical Engineering");

        assertFalse(exists);
    }
}
