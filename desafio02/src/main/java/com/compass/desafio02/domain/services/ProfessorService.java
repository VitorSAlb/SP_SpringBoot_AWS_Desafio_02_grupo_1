package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.repositories.ProfessorRepository;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.domain.repositories.projection.ProfessorProjection;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.infrastructure.exceptions.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public void validateProfessorSubjects(Professor professor) {
        List<Subject> subjectsAsMain = subjectRepository.findByMainProfessor(professor);
        List<Subject> subjectsAsSubstitute = subjectRepository.findBySubstituteProfessor(professor);

        int totalSubjects = subjectsAsMain.size() + subjectsAsSubstitute.size();

        if (totalSubjects > 3) {
            throw new BusinessRuleException("A professor can participate in a maximum of 3 subjects.");
        }

        if (subjectsAsMain.size() > 1) {
            throw new BusinessRuleException("A professor can only be a main professor in one subject.");
        }

        long substituteInSameCourse = subjectsAsSubstitute.stream()
                .filter(subject -> subject.getCourse().equals(professor.getCourse()))
                .count();

        if (substituteInSameCourse > 1) {
            throw new BusinessRuleException("A professor can only be a substitute in one subject within their own course.");
        }

        long substituteInOtherCourses = subjectsAsSubstitute.stream()
                .filter(subject -> !subject.getCourse().equals(professor.getCourse()))
                .count();

        if (substituteInOtherCourses > 1) {
            throw new BusinessRuleException("A professor can only be a substitute in one subject of another course.");
        }
    }

    public Professor save(Professor professor) {
        if (!isPasswordValid(professor.getPassword())) {
            throw new IllegalArgumentException("The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.");
        }
        return professorRepository.save(professor);
    }

    public Professor findById(Integer id) {
        return professorRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Professor not found") // CHANGE EXCEPTION
        );
    }

    public Professor findByEmail(String email) {
        try {
            return professorRepository.findByEmail(email);
        } catch (RuntimeException e) {
            throw new RuntimeException("Email not found"); // CHANGE EXCEPTION
        }
    }

    public Page<ProfessorProjection> findAll(Pageable pageable) {
        return professorRepository.findAllP(pageable);
    }

    public Professor update(Integer id, Professor newProfessor) {
        Professor existingProfessor = findById(id);

        existingProfessor.setEmail(newProfessor.getEmail());
        existingProfessor.setFirstName(newProfessor.getFirstName());
        existingProfessor.setLastName(newProfessor.getLastName());
        existingProfessor.setBirthdate(newProfessor.getBirthdate());

        return professorRepository.save(existingProfessor);
    }

    public void delete(Integer id) {
        professorRepository.deleteById(id);
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");
    }
}

