package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.repositories.ProfessorRepository;
import com.compass.desafio02.domain.repositories.StudentRepository;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.infrastructure.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProfessorRepository professorRepository;

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

    public Subject findByName(String name) {
        try {
            return subjectRepository.findByName(name);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<Subject> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    public Subject update(String name, Subject updatedSubject) {
        Subject existingSubject = findByName(name);

        existingSubject.setName(updatedSubject.getName());
        existingSubject.setDescription(updatedSubject.getDescription());
        existingSubject.setMainProfessor(updatedSubject.getMainProfessor());
        existingSubject.setSubstituteProfessor(updatedSubject.getSubstituteProfessor());
        existingSubject.setCourse(updatedSubject.getCourse());
        existingSubject.setStudents(updatedSubject.getStudents());

        return subjectRepository.save(existingSubject);
    }

    public void delete(String name) {
        Subject subject = findByName(name);

        if (!subjectRepository.existsById(subject.getId())) {
            throw new ResourceNotFoundException("Subject not found with Name: " + name);
        }
        subjectRepository.deleteById(subject.getId());
    }

    public void addStudentToSubject(String subjectName, String studentEmail) {

        Subject subject = findByName(subjectName);
        Student student = studentRepository.findByEmail(studentEmail);

        if (student == null || subject == null) {
            throw new UserOrSubjectNotFoundException("Student or Subject not found.");
        }
        if (!student.getSubjects().isEmpty()) {
            for (Subject items : student.getSubjects() ) {
                if (Objects.equals(items.getName(), subject.getName())) {
                    throw new UserAlreadyAssignedException("Student is already assigned in " + subjectName + ".");
                }
            }
        }

        student.addSubject(subject);
        subjectRepository.save(subject);
    }

    public void removeStudentFromSubject(String subjectName, String studentEmail) {

        Subject subject = findByName(subjectName);
        Student student = studentRepository.findByEmail(studentEmail);

        if (student == null || subject == null) {
            throw new UserOrSubjectNotFoundException("Student or Subject not found.");
        }

        if (student.getSubjects().isEmpty()) {
            throw new InvalidCredentialsException("Student not have subjects.");
        }

        for (Subject items : student.getSubjects() ) {
            if (Objects.equals(items.getName(), subject.getName())) {
                student.removeSubject(subject);
                subjectRepository.save(subject);
            }
        }
    }

    public Subject addProfessorToSubject(String subjectName, String professorEmail) {

        Subject subject = findByName(subjectName);
        Professor professor = professorRepository.findByEmail(professorEmail);

        if (professor == null || subject == null) {
            throw new UserOrSubjectNotFoundException("Professor or Subject not found.");
        }

        if (professor.getSubjectHolder().equals(subject)) {
            throw new UserAlreadyAssignedException("Professor Already Assigned in this subject.");
        }

        if (professor.getSubjectSub().equals(subject)) {
            throw new UserAlreadyAssignedException("Professor Already Assigned in this subject.");
        }

        if (professor.getSubjectHolder().isEmpty()) {
            professor.addSubjectHolder(subject);
            subjectRepository.save(subject);
            return subject;
        }

        if (!professor.getSubjectHolder().isEmpty() && professor.getSubjectSub().isEmpty()) {
            professor.addSubjectSub(subject);
            subjectRepository.save(subject);
            return subject;
        }

        return subject;
    }
}
