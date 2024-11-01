package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.repositories.StudentRepository;
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

    @Autowired
    private StudentRepository studentRepository;

    public Subject save(Subject subject) {

        if (subject.getMainProfessor() == null || subject.getSubstituteProfessor() == null) {
            throw new BusinessRuleException("The subject must have both a main and substitute professor.");
        }

        if (subject.getMainProfessor().getId().equals(subject.getSubstituteProfessor().getId())) {
            throw new BusinessRuleException("The main professor and substitute cannot be the same person.");
        }

        if (subject.getStudents() != null && subject.getStudents().size() > 10) {
            throw new BusinessRuleException("A subject cannot have more than 10 students.");
        }

        return subjectRepository.save(subject);
    }

    public Subject findById(Integer id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
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

        return subjectRepository.save(existingSubject);
    }

    public void delete(Integer id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
    }
}
