package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.projection.CoordinatorProjection;
import com.compass.desafio02.domain.services.CoordinatorService;
import com.compass.desafio02.infrastructure.exceptions.DuplicateException;
import com.compass.desafio02.infrastructure.exceptions.user.*;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CoordinatorServiceTest {

    @Mock
    private CoordinatorRepository coordinatorRepository;

    @InjectMocks
    private CoordinatorService coordinatorService;

    private Coordinator coordinator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        coordinator = new Coordinator();
        coordinator.setId(1);
        coordinator.setEmail("test@domain.com");
        coordinator.setPassword("Password123!");
    }

    @Test
    void testSaveCoordinator_EmailAlreadyExists() {
        when(coordinatorRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> coordinatorService.save(coordinator));
        verify(coordinatorRepository, never()).save(any(Coordinator.class));
    }

    @Test
    void testFindById_Success() {
        when(coordinatorRepository.findById(anyInt())).thenReturn(Optional.of(coordinator));

        Coordinator foundCoordinator = coordinatorService.findById(1);

        assertNotNull(foundCoordinator);
        assertEquals(coordinator.getId(), foundCoordinator.getId());
        verify(coordinatorRepository, times(1)).findById(anyInt());
    }

    @Test
    void testFindById_NotFound() {
        when(coordinatorRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> coordinatorService.findById(1));
    }

    @Test
    void testFindByEmail_Success() {
        when(coordinatorRepository.findByEmail(anyString())).thenReturn(coordinator);

        Coordinator foundCoordinator = coordinatorService.findByEmail("test@domain.com");

        assertNotNull(foundCoordinator);
        assertEquals(coordinator.getEmail(), foundCoordinator.getEmail());
        verify(coordinatorRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void testFindByEmail_NotFound() {
        when(coordinatorRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> coordinatorService.findByEmail("test@domain.com"));
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CoordinatorProjection> coordinatorPage = new PageImpl<>(List.of());

        when(coordinatorRepository.findAllP(pageable)).thenReturn(coordinatorPage);

        Page<CoordinatorProjection> result = coordinatorService.findAll(pageable);

        assertNotNull(result);
        verify(coordinatorRepository, times(1)).findAllP(pageable);
    }

    @Test
    void testUpdateCoordinator_Success() {
        when(coordinatorRepository.findByEmail(anyString())).thenReturn(coordinator);
        when(coordinatorRepository.save(any(Coordinator.class))).thenReturn(coordinator);

        Coordinator updatedCoordinator = new Coordinator();
        updatedCoordinator.setEmail("newtest@domain.com");
        updatedCoordinator.setFirstName("NewName");

        Coordinator result = coordinatorService.update("test@domain.com", updatedCoordinator);

        assertNotNull(result);
        assertEquals(updatedCoordinator.getEmail(), result.getEmail());
        verify(coordinatorRepository, times(1)).save(any(Coordinator.class));
    }

    @Test
    void testDeleteCoordinator_Success() {
        when(coordinatorRepository.findByEmail(anyString())).thenReturn(coordinator);
        when(coordinatorRepository.existsById(anyInt())).thenReturn(true);

        coordinatorService.delete("test@domain.com");

        verify(coordinatorRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteCoordinator_NotFound() {
        when(coordinatorRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> coordinatorService.delete("test@domain.com"));
        verify(coordinatorRepository, never()).deleteById(anyInt());
    }

    @Test
    void testEditPassword_Success() {
        when(coordinatorRepository.findByEmail(anyString())).thenReturn(coordinator);

        coordinatorService.editPassword("test@domain.com", "Password123!", "NewPassword123!", "NewPassword123!");

        assertEquals("NewPassword123!", coordinator.getPassword());
        verify(coordinatorRepository, times(1)).save(any(Coordinator.class));
    }

    @Test
    void testEditPassword_PasswordsDoNotMatch() {
        assertThrows(PasswordUpdateException.class, () -> coordinatorService.editPassword(
                "test@domain.com", "Password123!", "NewPassword123!", "WrongPassword"));
        verify(coordinatorRepository, never()).save(any(Coordinator.class));
    }
}
