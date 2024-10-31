package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.ProfessorRepository;
import com.compass.desafio02.domain.repositories.projection.ProfessorProjection;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor findById(Integer id) {
        return professorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Professor not founded") // TROCAR EXCESSÃO
        );
    }

    public Student findByEmail(String email) {
        try {
            return professorRepository.findByEmail(email);
        } catch (RuntimeException e) {
            throw new RuntimeException("Email not founded"); // TROCAR EXCESSÃO
        }
    }

    public Page<ProfessorProjection> findAll(Pageable pageable) {
        return professorRepository.findAllP(pageable);
    }

    public Professor update(Integer id, Professor newProfessor) {
        Professor existingProfessor = findById(id);

        existingProfessor.setEmail(newProfessor.getEmail()); // TESMOS QUE VERIFICAR SE O NOVO EMAIL JÁ EXISTE PARA PODER EDITAR
        existingProfessor.setFirstName(newProfessor.getFirstName());
        existingProfessor.setLastName(newProfessor.getLastName());
        existingProfessor.setBirthdate(newProfessor.getBirthdate());

        return professorRepository.save(existingProfessor);
    }

    public void delete(Integer id) {
        professorRepository.deleteById(id);
    }
}
