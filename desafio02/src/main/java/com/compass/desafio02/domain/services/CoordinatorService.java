package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CoordinatorService {

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    public Coordinator save(Coordinator professor) {
        return coordinatorRepository.save(professor);
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

    public Page<Coordinator> findAll(Pageable pageable) {
        return coordinatorRepository.findAll(pageable);
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
}
