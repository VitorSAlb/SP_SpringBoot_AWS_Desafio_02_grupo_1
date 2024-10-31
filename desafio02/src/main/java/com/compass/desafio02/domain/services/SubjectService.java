package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import com.compass.desafio02.infrastructure.exceptions.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public Subject save(Subject subject) {


        //Trocar para ingles dps
        if (subject.getMainProfessor() == null || subject.getSubstituteProfessor() == null) {
            throw new BusinessRuleException("A disciplina deve ter um professor titular e um substituto.");
        }

        if (subject.getMainProfessor().getId().equals(subject.getSubstituteProfessor().getId())) {
            throw new BusinessRuleException("O professor titular e o substituto não podem ser a mesma pessoa.");
        }

        if (subject.getStudents() != null && subject.getStudents().size() > 10) {
            throw new BusinessRuleException("Uma disciplina não pode ter mais de 10 alunos.");
        }

        return subjectRepository.save(subject);
    }

    public Subject findById(Integer id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada com o id: " + id));
    }

    public Page<Subject> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    public Subject update(Integer id, Subject updatedSubject) {
        Subject existingSubject = findById(id);

        existingSubject.setName(updatedSubject.getName());
        existingSubject.setDescription(updatedSubject.getDescription());
        existingSubject.setMainProfessor(updatedSubject.getMainProfessor());
        existingSubject.setSubstituteProfessor(updatedSubject.getSubstituteProfessor());
        existingSubject.setCourse(updatedSubject.getCourse());
        existingSubject.setStudents(updatedSubject.getStudents());

        if (existingSubject.getMainProfessor() == null || existingSubject.getSubstituteProfessor() == null) {
            throw new BusinessRuleException("A disciplina deve ter um professor titular e um substituto.");
        }

        if (existingSubject.getMainProfessor().getId().equals(existingSubject.getSubstituteProfessor().getId())) {
            throw new BusinessRuleException("O professor titular e o substituto não podem ser a mesma pessoa.");
        }

        if (existingSubject.getStudents() != null && existingSubject.getStudents().size() > 10) {
            throw new BusinessRuleException("Uma disciplina não pode ter mais de 10 alunos.");
        }

        return subjectRepository.save(existingSubject);
    }

    public void delete(Integer id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Disciplina não encontrada com o id: " + id);
        }
        subjectRepository.deleteById(id);
    }
}
