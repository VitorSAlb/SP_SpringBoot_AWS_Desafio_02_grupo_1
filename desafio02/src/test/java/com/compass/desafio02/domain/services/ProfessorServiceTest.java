package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.repositories.ProfessorRepository;
import com.compass.desafio02.domain.repositories.projection.ProfessorProjection;
import com.compass.desafio02.infrastructure.exceptions.DuplicateException;
import com.compass.desafio02.infrastructure.exceptions.user.UserNotFoundException;
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

class ProfessorServiceTest {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorService professorService;

    private Professor professor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        professor = new Professor();
        professor.setId(1);
        professor.setEmail("test@domain.com");
        professor.setPassword("Password123!");
    }

    @Test
    void testSaveProfessor_Success() {
        when(professorRepository.existsByEmail(anyString())).thenReturn(false);
        when(professorRepository.save(any(Professor.class))).thenReturn(professor);

        Professor savedProfessor = professorService.save(professor);

        assertNotNull(savedProfessor);
        assertEquals(professor.getEmail(), savedProfessor.getEmail());
        verify(professorRepository, times(1)).save(any(Professor.class));
    }

    @Test
    void testSaveProfessor_EmailAlreadyExists() {
        when(professorRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> professorService.save(professor));
        verify(professorRepository, never()).save(any(Professor.class));
    }

    @Test
    void testFindById_Success() {
        when(professorRepository.findById(anyInt())).thenReturn(Optional.of(professor));

        Professor foundProfessor = professorService.findById(1);

        assertNotNull(foundProfessor);
        assertEquals(professor.getId(), foundProfessor.getId());
        verify(professorRepository, times(1)).findById(anyInt());
    }

    @Test
    void testFindById_NotFound() {
        when(professorRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> professorService.findById(1));
    }

    @Test
    void testFindByEmail_Success() {
        when(professorRepository.findByEmail(anyString())).thenReturn(professor);

        Professor foundProfessor = professorService.findByEmail("test@domain.com");

        assertNotNull(foundProfessor);
        assertEquals(professor.getEmail(), foundProfessor.getEmail());
        verify(professorRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testFindByEmail_NotFound() {
        when(professorRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> professorService.findByEmail("test@domain.com"));
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProfessorProjection> professorPage = new PageImpl<>(List.of());

        when(professorRepository.findAllP(pageable)).thenReturn(professorPage);

        Page<ProfessorProjection> result = professorService.findAll(pageable);

        assertNotNull(result);
        verify(professorRepository, times(1)).findAllP(pageable);
    }

    @Test
    void testUpdateProfessor_Success() {
        when(professorRepository.findByEmail(anyString())).thenReturn(professor);
        when(professorRepository.save(any(Professor.class))).thenReturn(professor);

        Professor updatedProfessor = new Professor();
        updatedProfessor.setEmail("newtest@domain.com");
        updatedProfessor.setFirstName("NewName");

        Professor result = professorService.update("test@domain.com", updatedProfessor);

        assertNotNull(result);
        assertEquals(updatedProfessor.getEmail(), result.getEmail());
        verify(professorRepository, times(1)).save(any(Professor.class));
    }

    @Test
    void testDeleteProfessor_Success() {
        when(professorRepository.findByEmail(anyString())).thenReturn(professor);
        when(professorRepository.existsById(anyInt())).thenReturn(true);

        professorService.delete("test@domain.com");

        verify(professorRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteProfessor_NotFound() {
        when(professorRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> professorService.delete("test@domain.com"));
        verify(professorRepository, never()).deleteById(anyInt());
    }

    @Test
    void testAddCourse() {
        Course course = new Course();
        course.setName("Test Course");

        professorService.addCourse(course, professor);

        assertEquals(course, professor.getCourse());
        verify(professorRepository, times(1)).save(any(Professor.class));
    }

    @Test
    void testRemoveCourse() {
        professor.setCourse(new Course());

        professorService.removeCourse(professor);

        assertNull(professor.getCourse());
        verify(professorRepository, times(1)).save(any(Professor.class));
    }
}
