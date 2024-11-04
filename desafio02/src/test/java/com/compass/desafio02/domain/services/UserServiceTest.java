package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.User;
import com.compass.desafio02.domain.repositories.UserRepository;
import com.compass.desafio02.infrastructure.exceptions.user.PasswordUpdateException;
import com.compass.desafio02.infrastructure.exceptions.user.UserCreationException;
import com.compass.desafio02.infrastructure.exceptions.user.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
    }

    @Test
    void testSaveUser_Success() {
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.Save(user);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("password123");
    }


    @Test
    void testSaveUser_DuplicateEmail_ThrowsException() {
        when(userRepository.save(any(User.class))).thenThrow(new org.springframework.dao.DataIntegrityViolationException("Duplicate entry"));
        assertThrows(UserCreationException.class, () -> userService.Save(user));
    }

    @Test
    void testFindById_UserFound() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1);

        assertNotNull(foundUser);
        assertEquals(1, foundUser.getId());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_UserNotFound_ThrowsException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(1));
    }

    @Test
    void testEditPass_Success() {
        String oldPass = "oldPass";
        String newPass = "newPass";
        String confirmPass = "newPass";

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPass, "password123")).thenReturn(true); // A senha real do usuário
        when(passwordEncoder.encode(newPass)).thenReturn("encodedNewPass");
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.editPass(1, oldPass, newPass, confirmPass);

        assertNotNull(updatedUser);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
        verify(passwordEncoder, times(1)).matches(oldPass, "password123");
        verify(passwordEncoder, times(1)).encode(newPass);
    }

    @Test
    void testEditPass_PasswordMismatch_ThrowsException() {
        String oldPass = "oldPass";
        String newPass = "newPass";
        String confirmPass = "differentPass";

        assertThrows(PasswordUpdateException.class, () -> userService.editPass(1, oldPass, newPass, confirmPass));
    }

    @Test
    void testFindAllUsers() {
        userService.findAll();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindByEmail_UserFound() {
        when(userRepository.findByEmailUser(user.getEmail())).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
        verify(userRepository, times(1)).findByEmailUser(user.getEmail());
    }

    @Test
    void testFindByEmail_UserNotFound_ThrowsException() {
        when(userRepository.findByEmailUser(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findByEmail(user.getEmail()));
    }
}
