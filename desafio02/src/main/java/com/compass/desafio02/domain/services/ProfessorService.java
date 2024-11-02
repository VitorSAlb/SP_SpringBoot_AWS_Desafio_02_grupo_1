package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.domain.repositories.ProfessorRepository;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.domain.repositories.projection.ProfessorProjection;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.infrastructure.exceptions.BusinessRuleException;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import com.compass.desafio02.infrastructure.exceptions.user.PasswordUpdateException;
import com.compass.desafio02.infrastructure.exceptions.user.UserCreationException;
import com.compass.desafio02.infrastructure.exceptions.user.UserDeletionException;
import com.compass.desafio02.infrastructure.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public void addMainProfessor(String emailProfessor, String nameCourse, String subjectName) {
        Professor professor = findByEmail(emailProfessor);
        Subject subject = subjectRepository.findByNameAndCourseName(subjectName, nameCourse)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with name: " + subjectName + " in course: " + nameCourse));

        validateMainProfessorAssignment(professor, subject);

        subject.setMainProfessor(professor);
        subjectRepository.save(subject);
    }

    public void addSubstituteProfessor(String emailProfessor, String nameCourse, String subjectName) {
        Professor professor = findByEmail(emailProfessor);
        Subject subject = subjectRepository.findByNameAndCourseName(subjectName, nameCourse)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with name: " + subjectName + " in course: " + nameCourse));

        validateSubstituteProfessorAssignment(professor, subject);

        subject.setSubstituteProfessor(professor);
        subjectRepository.save(subject);
    }

    public void removeMainProfessor(String emailProfessor, String nameCourse, String subjectName) {
        Professor professor = findByEmail(emailProfessor);
        Subject subject = subjectRepository.findByNameAndCourseName(subjectName, nameCourse)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with name: " + subjectName + " in course: " + nameCourse));

        if (subject.getMainProfessor() != null && subject.getMainProfessor().equals(professor)) {
            subject.setMainProfessor(null);
            subjectRepository.save(subject);
        }
    }

    public void removeSubstituteProfessor(String emailProfessor, String nameCourse, String subjectName) {
        Professor professor = findByEmail(emailProfessor);
        Subject subject = subjectRepository.findByNameAndCourseName(subjectName, nameCourse)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with name: " + subjectName + " in course: " + nameCourse));

        if (subject.getSubstituteProfessor() != null && subject.getSubstituteProfessor().equals(professor)) {
            subject.setSubstituteProfessor(null);
            subjectRepository.save(subject);
        }
    }

    private void validateMainProfessorAssignment(Professor professor, Subject subject) {
        List<Subject> subjectsAsMain = subjectRepository.findByMainProfessor(professor);
        if (!subjectsAsMain.isEmpty()) {
            throw new BusinessRuleException("A professor can only be a main professor in one subject.");
        }
        validateProfessorSubjects(professor);
    }

    private void validateSubstituteProfessorAssignment(Professor professor, Subject subject) {
        validateProfessorSubjects(professor);
    }

    private void validateProfessorSubjects(Professor professor) {
        List<Subject> subjectsAsMain = subjectRepository.findByMainProfessor(professor);
        List<Subject> subjectsAsSubstitute = subjectRepository.findBySubstituteProfessor(professor);

        if (subjectsAsMain.size() > 1) {
            throw new BusinessRuleException("A professor can only be a main professor in one subject.");
        }
        if ((subjectsAsMain.size() + subjectsAsSubstitute.size()) > 3) {
            throw new BusinessRuleException("A professor can participate in a maximum of 3 subjects.");
        }
    }

    public Professor save(Professor professor) {
        if (!isPasswordValid(professor.getPassword())) {
            throw new IllegalArgumentException("The password must have at least one uppercase letter, one lowercase letter, one number, one special character and at least 8 characters.");
        }

        try {
            return professorRepository.save(professor);
        } catch (Exception e) {
            throw new UserCreationException("Error saving professor: " + e.getMessage());
        }
    }

    public Professor findById(Integer id) {
        return professorRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Professor not found with ID: " + id)
        );
    }

    public Professor findByEmail(String email) {
        try {
            return professorRepository.findByEmail(email);
        } catch (RuntimeException e) {
            throw new UserNotFoundException("Email not found with email: " + email);
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

    public void delete(String email) {
        Professor professor = findByEmail(email);

        if (!professorRepository.existsById(professor.getId())) {
            throw new UserDeletionException("Cannot delete professor: Professor not found with Email: " + email);
        }

        try {
            professorRepository.deleteById(professor.getId());
        } catch (Exception e) {
            throw new UserDeletionException("Error deleting professor: " + e.getMessage());
        }
    }
  
    public void editPassword(Integer id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordUpdateException("New password and confirmation password do not match.");
        }

        if (!isPasswordValid(newPassword)) {
            throw new PasswordUpdateException("Password does not meet security requirements.");
        }

        Professor professor = findById(id);

        if (!Objects.equals(professor.getPassword(), currentPassword)) {
            throw new PasswordUpdateException("Current password is incorrect.");
        }

        professor.setPassword(newPassword);
        try {
            professorRepository.save(professor);
        } catch (Exception e) {
            throw new PasswordUpdateException("Error updating password: " + e.getMessage());
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$");
    }
}

