package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.ProfessorRepository;
import com.compass.desafio02.domain.repositories.projection.CoordinatorProjection;
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

        return coordinatorRepository.save(coordinator);
    }

    public Coordinator findById(Integer id) {
        return coordinatorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Professor not founded") // TROCAR EXCESSÃO
        );
    }

    public Coordinator findByEmail(String email) {
        try {
            return coordinatorRepository.findByEmail(email);
        } catch (RuntimeException e) {
            throw new RuntimeException("Email not founded"); // TROCAR EXCESSÃO
        }

    }

    public Page<CoordinatorProjection> findAll(Pageable pageable) {
        return coordinatorRepository.findAllP(pageable);
    }

    public Coordinator update(Integer id, Coordinator newCoordinator) {
        Coordinator existingProfessor = findById(id);

        existingProfessor.setEmail(newCoordinator.getEmail()); // TESMOS QUE VERIFICAR SE O NOVO EMAIL JÁ EXISTE PARA PODER EDITAR
        existingProfessor.setFirstName(newCoordinator.getFirstName());
        existingProfessor.setLastName(newCoordinator.getLastName());
        existingProfessor.setBirthdate(newCoordinator.getBirthdate());

        return coordinatorRepository.save(existingProfessor);
    }

    public void delete(Integer id) {
        coordinatorRepository.deleteById(id);
    }

    public void editPassword(Integer id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException(); // TROCAR EXCESSÃO
        }

        Coordinator coordinator = findById(id);

        if (!Objects.equals(coordinator.getPassword(), currentPassword)) {
            throw new RuntimeException(); // TROCAR EXCESSÃO
        }
        coordinator.setPassword(newPassword);
        coordinatorRepository.save(coordinator);
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");
    }
}
