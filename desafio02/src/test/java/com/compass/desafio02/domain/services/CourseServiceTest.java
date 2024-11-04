package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.infrastructure.exceptions.*;
import com.compass.desafio02.domain.services.CoordinatorService;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.domain.services.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CoordinatorRepository coordinatorRepository;

    @Mock
    private SubjectService subjectService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setId(1);
        course.setName("Course Test");
        course.setDescription("Test description");
    }

    @Test
    void testSaveCourse_Success() {
        when(courseRepository.existsByName(anyString())).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course savedCourse = courseService.save(course);

        assertNotNull(savedCourse);
        assertEquals(course.getName(), savedCourse.getName());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testSaveCourse_NameEmpty() {
        course.setName("");

        assertThrows(EmptyFieldException.class, () -> courseService.save(course));
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testSaveCourse_DescriptionEmpty() {
        course.setDescription("");

        assertThrows(EmptyFieldException.class, () -> courseService.save(course));
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testSaveCourse_NameAlreadyExists() {
        when(courseRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> courseService.save(course));
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testFindById_Success() {
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));

        Course foundCourse = courseService.findById(1);

        assertNotNull(foundCourse);
        assertEquals(course.getId(), foundCourse.getId());
        verify(courseRepository, times(1)).findById(anyInt());
    }

    @Test
    void testFindById_NotFound() {
        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.findById(1));
    }

    @Test
    void testFindByName_Success() {
        when(courseRepository.findByName(anyString())).thenReturn(course);

        Course foundCourse = courseService.findByName("Course Test");

        assertNotNull(foundCourse);
        assertEquals(course.getName(), foundCourse.getName());
        verify(courseRepository, times(1)).findByName(anyString());
    }

    @Test
    void testFindByName_NotFound() {
        when(courseRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> courseService.findByName("Course Test"));
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> coursePage = new PageImpl<>(List.of(course));

        when(courseRepository.findAllP(pageable)).thenReturn(coursePage);

        Page<Course> result = courseService.findAll(pageable);

        assertNotNull(result);
        verify(courseRepository, times(1)).findAllP(pageable);
    }

    @Test
    void testUpdateCourse_Success() {
        when(courseRepository.findByName(anyString())).thenReturn(course);
        when(courseRepository.existsByName(anyString())).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course updatedCourse = new Course();
        updatedCourse.setName("New Course Test");
        updatedCourse.setDescription("New description");

        Course result = courseService.update("Course Test", updatedCourse);

        assertNotNull(result);
        assertEquals(updatedCourse.getName(), result.getName());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourse_NameAlreadyExists() {
        when(courseRepository.findByName(anyString())).thenReturn(course);
        when(courseRepository.existsByName(anyString())).thenReturn(true);

        Course updatedCourse = new Course();
        updatedCourse.setName("New Course Test");

        assertThrows(InvalidCredentialsException.class, () -> courseService.update("Course Test", updatedCourse));
    }

    @Test
    void testUpdateCourse_EmptyName() {
        Course existingCourse = new Course();
        existingCourse.setName("Course Test");
        existingCourse.setDescription("Description Test");

        when(courseRepository.findByName("Course Test")).thenReturn(existingCourse);

        Course updatedCourse = new Course();
        updatedCourse.setName("");


        assertThrows(EmptyFieldException.class, () -> courseService.update("Course Test", updatedCourse));
    }


    @Test
    void testDeleteCourse_Success() {
        when(courseRepository.findByName(anyString())).thenReturn(course);
        when(courseRepository.existsById(anyInt())).thenReturn(true);

        courseService.delete("Course Test");

        verify(courseRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteCourse_NotFound() {
        when(courseRepository.findByName(anyString())).thenReturn(course);
        when(courseRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> courseService.delete("Course Test"));
    }

    @Test
    void testAddCoordinatorToCourse_Success() {
        Coordinator coordinator = new Coordinator();
        coordinator.setId(1);
        coordinator.setEmail("coordinator@domain.com");

        when(courseRepository.findByName(anyString())).thenReturn(course);
        when(coordinatorRepository.findByEmail(anyString())).thenReturn(coordinator);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.addCoordinatorToCourse("Course Test", "coordinator@domain.com");

        assertNotNull(result);
        assertEquals(coordinator, result.getCoordinator());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testAddCoordinatorToCourse_CoordinatorNotFound() {
        when(courseRepository.findByName(anyString())).thenReturn(course);
        when(coordinatorRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(CoordinatorOrCourseNotFoundException.class, () -> courseService.addCoordinatorToCourse("Course Test", "coordinator@domain.com"));
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testRemoveCoordinatorFromCourse_Success() {
        Coordinator coordinator = new Coordinator();
        course.setCoordinator(coordinator);

        when(courseRepository.findByName(anyString())).thenReturn(course);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        courseService.removeCoordinatorFromCourse("Course Test");

        assertNull(course.getCoordinator());
        verify(courseRepository, times(1)).save(any(Course.class));
    }
}
