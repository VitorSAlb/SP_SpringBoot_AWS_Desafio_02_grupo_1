package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.ProfessorRepository;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.domain.repositories.projection.CoordinatorProjection;
import com.compass.desafio02.infrastructure.exceptions.DuplicateException;
import com.compass.desafio02.infrastructure.exceptions.user.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CoordinatorService {

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Coordinator save(Coordinator coordinator) {
        if (coordinatorRepository.existsByEmail(coordinator.getEmail())) {
            throw new DuplicateException("Email already exists");
        }

        if (!isPasswordValid(coordinator.getPassword())) {
            throw new IllegalArgumentException("The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.");
        }

        coordinator.setPassword(bCryptPasswordEncoder.encode(coordinator.getPassword()));

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

        if (coordinator.getCourse() != null) {
            throw new UserDeletionException("Error Coordinator is linked to a course, remove this coordinator of course to delete with successfully");
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

//    @Transactional
//    public void assignCoordinatorAsProfessor(String subjectName, String coordinatorEmail, boolean isMainProfessor) {
//        Coordinator coordinator = coordinatorRepository.findByEmail(coordinatorEmail);
//        if (coordinator == null) {
//            throw new IllegalArgumentException("Coordinator not found with email: " + coordinatorEmail);
//        }
//
//        Professor professor = convertCoordinatorToProfessor(coordinator);
//
//        if (professor.getId() == null) {
//            professor = professorRepository.save(professor);
//        }
//
//        Subject subject = subjectRepository.findByName(subjectName);
//        if (subject == null) {
//            throw new IllegalArgumentException("Subject not found with name: " + subjectName);
//        }
//
//        if (isMainProfessor) {
//            subject.setMainProfessor(professor);
//        } else {
//            subject.setSubstituteProfessor(professor);
//        }
//
//        subjectRepository.save(subject);
//    }
//
//
//    private Professor convertCoordinatorToProfessor(Coordinator coordinator) {
//        Professor professor = new Professor();
//        professor.setFirstName(coordinator.getFirstName());
//        professor.setLastName(coordinator.getLastName());
//        professor.setEmail(coordinator.getEmail());
//        professor.setPassword(coordinator.getPassword());
//        professor.setRole(Role.ROLE_PROFESSOR);
//        professor.setBirthdate(coordinator.getBirthdate());
//        return professor;
//    }
}
