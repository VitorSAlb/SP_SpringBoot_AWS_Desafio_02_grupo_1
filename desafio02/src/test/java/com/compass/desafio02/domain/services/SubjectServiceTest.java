package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.infrastructure.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SubjectServiceTest {

    @InjectMocks
    private SubjectService subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private ProfessorService professorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSubject_Success() {
        Subject subject = new Subject();
        subject.setName("Mathematics");

        Professor mainProfessor = new Professor();
        mainProfessor.setEmail("main.professor@test.com");
        mainProfessor.setCourse(new Course());

        Professor substituteProfessor = new Professor();
        substituteProfessor.setEmail("substitute.professor@test.com");
        substituteProfessor.setCourse(new Course());

        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);

        when(subjectRepository.existsByName(anyString())).thenReturn(false);
        when(professorService.findByEmail(mainProfessor.getEmail())).thenReturn(mainProfessor);
        when(professorService.findByEmail(substituteProfessor.getEmail())).thenReturn(substituteProfessor);
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        Subject savedSubject = subjectService.save(subject);

        assertNotNull(savedSubject);
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }



    @Test
    void testSaveSubject_MainAndSubstituteSame() {
        Subject subject = new Subject();
        Professor professor = new Professor();
        professor.setEmail("professor@test.com");

        subject.setMainProfessor(professor);
        subject.setSubstituteProfessor(professor);

        assertThrows(DuplicateException.class, () -> subjectService.save(subject));
    }

    @Test
    void testFindById_SubjectNotFound() {
        when(subjectRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subjectService.findById(1));
    }

    @Test
    void testFindByName_SubjectNotFound() {
        when(subjectRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> subjectService.findByName("Mathematics"));
    }


    @Test
    void testDeleteSubject_Success() {
        Subject subject = new Subject();
        subject.setId(1);
        subject.setName("Mathematics");

        when(subjectRepository.findByName(anyString())).thenReturn(subject);
        when(subjectRepository.existsById(subject.getId())).thenReturn(true);

        subjectService.delete("Mathematics");

        verify(subjectRepository, times(1)).deleteById(subject.getId());
    }

    @Test
    void testDeleteSubject_SubjectNotFound() {
        when(subjectRepository.findByName(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> subjectService.delete("Mathematics"));
    }

    @Test
    void testSaveSubject_MoreThan10Students() {
        Subject subject = new Subject();
        subject.setName("Mathematics");

        Professor mainProfessor = new Professor();
        mainProfessor.setEmail("main.professor@test.com");

        Professor substituteProfessor = new Professor();
        substituteProfessor.setEmail("substitute.professor@test.com");

        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);

        subject.getStudents().addAll(new ArrayList<>(Collections.nCopies(11, new Student())));

        when(subjectRepository.existsByName(anyString())).thenReturn(false);
        when(professorService.findByEmail(anyString())).thenReturn(mainProfessor, substituteProfessor);

        assertThrows(BusinessRuleException.class, () -> subjectService.save(subject));
    }

}
