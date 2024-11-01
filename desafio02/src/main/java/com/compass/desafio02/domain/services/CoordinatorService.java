package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.projection.CoordinatorProjection;
import com.compass.desafio02.infrastructure.exceptions.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CoordinatorService {

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    public Coordinator save(Coordinator coordinator) {
        if (!isPasswordValid(coordinator.getPassword())) {
            throw new IllegalArgumentException("The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.");
        }

        try {
            return coordinatorRepository.save(coordinator);
        } catch (Exception e) {
            throw new UserCreationException("Error saving coordinator: " + e.getMessage());
        }
    }

    public Coordinator findById(Integer id) {
        return coordinatorRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Coordinator not found with ID: " + id)
        );
    }

    public Coordinator findByEmail(String email) {
        Coordinator coordinator = coordinatorRepository.findByEmail(email);
        if (coordinator == null) {
            throw new UserNotFoundException("Coordinator not found with email: " + email);
        }
        return coordinator;
    }

    public Page<CoordinatorProjection> findAll(Pageable pageable) {
        return coordinatorRepository.findAllP(pageable);
    }

    public Coordinator update(String email, Coordinator newCoordinator) {
        Coordinator existingProfessor = findByEmail(email);
        try {
            existingProfessor.setEmail(newCoordinator.getEmail());
            existingProfessor.setFirstName(newCoordinator.getFirstName());
            existingProfessor.setLastName(newCoordinator.getLastName());
            existingProfessor.setBirthdate(newCoordinator.getBirthdate());
            return coordinatorRepository.save(existingProfessor);
        } catch (Exception e) {
            throw new UserUpdateException("Error updating coordinator: " + e.getMessage());
        }
    }

    public void delete(String email) {
        Coordinator coordinator = findByEmail(email);

        if (!coordinatorRepository.existsById(coordinator.getId())) {
            throw new UserDeletionException("Cannot delete coordinator: Coordinator not found with Email: " + email);
        }

        try {
            coordinatorRepository.deleteById(coordinator.getId());
        } catch (Exception e) {
            throw new UserDeletionException("Error deleting Coordinator: " + e.getMessage());
        }
    }

    public void editPassword(String email, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordUpdateException("New password and confirmation password do not match.");
        }

        if (!isPasswordValid(newPassword)) {
            throw new PasswordUpdateException("The password does not meet security requirements.");
        }

        Coordinator coordinator = findByEmail(email);

        if (!Objects.equals(coordinator.getPassword(), currentPassword)) {
            throw new PasswordUpdateException("Current password is incorrect.");
        }

        coordinator.setPassword(newPassword);
        try {
            coordinatorRepository.save(coordinator);
        } catch (Exception e) {
            throw new PasswordUpdateException("Error updating password: " + e.getMessage());
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");
    }
}
