package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.StudentRepository;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import com.compass.desafio02.feign.ViaCepClient;
import com.compass.desafio02.infrastructure.exceptions.DuplicateException;
import com.compass.desafio02.infrastructure.exceptions.user.*;
import com.compass.desafio02.web.dto.feign.CepDto;
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
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ViaCepClient viaCepClient;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1);
        student.setEmail("student@example.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setPassword("Password123!");
        student.setCep("12345678");
    }

    @Test
    void testSaveStudent_Success() {
        when(studentRepository.existsByEmail(anyString())).thenReturn(false);
        when(viaCepClient.getAddressByCep(anyString()))
                .thenReturn(new CepDto("12345-678", "Rua Teste", "Bairro Teste", "Cidade Teste", "SP"));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student savedStudent = studentService.save(student);

        assertNotNull(savedStudent);
        assertEquals(student.getEmail(), savedStudent.getEmail());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testSaveStudent_EmailAlreadyExists() {
        when(studentRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> studentService.save(student));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testSaveStudent_InvalidPassword() {
        student.setPassword("invalid");
        assertThrows(UserCreationException.class, () -> studentService.save(student));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testFindById_Success() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));

        Student foundStudent = studentService.findById(1);

        assertNotNull(foundStudent);
        assertEquals(student.getId(), foundStudent.getId());
        verify(studentRepository, times(1)).findById(anyInt());
    }

    @Test
    void testFindById_NotFound() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> studentService.findById(1));
        verify(studentRepository, times(1)).findById(anyInt());
    }

    @Test
    void testUpdateStudent_Success() {
        when(studentRepository.findByEmail(anyString())).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student updatedStudent = new Student();
        updatedStudent.setEmail("updated@example.com");
        updatedStudent.setFirstName("Jane");
        updatedStudent.setLastName("Doe");

        Student result = studentService.update(student.getEmail(), updatedStudent);

        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testUpdateStudent_NotFound() {
        when(studentRepository.findByEmail(anyString())).thenReturn(null);

        Student updatedStudent = new Student();
        updatedStudent.setEmail("updated@example.com");

        assertThrows(UserNotFoundException.class, () -> studentService.update("nonexistent@example.com", updatedStudent));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testDeleteStudentById_Success() {
        when(studentRepository.existsById(anyInt())).thenReturn(true);

        studentService.delete(1);

        verify(studentRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteStudentById_NotFound() {
        when(studentRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(UserDeletionException.class, () -> studentService.delete(1));
        verify(studentRepository, never()).deleteById(anyInt());
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<StudentProjection> studentPage = new PageImpl<>(List.of());

        when(studentRepository.findAllP(any(Pageable.class))).thenReturn(studentPage);

        Page<StudentProjection> result = studentService.findAll(pageable);

        assertNotNull(result);
        verify(studentRepository, times(1)).findAllP(any(Pageable.class));
    }
}
