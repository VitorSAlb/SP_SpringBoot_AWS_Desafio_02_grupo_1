package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import com.compass.desafio02.infrastructure.exceptions.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    public Subject save(Subject subject, Integer mainProfessorId, Integer substituteProfessorId, Integer courseId, List<Integer> studentIds) {

        if (subjectRepository.existsByName(subject.getName())) {
            throw new BusinessRuleException("Subject name must be unique.");
        }

        Professor mainProfessor = professorService.findById(mainProfessorId);
        Professor substituteProfessor = professorService.findById(substituteProfessorId);

        if (mainProfessorId.equals(substituteProfessorId)) {
            throw new BusinessRuleException("Main professor and substitute professor cannot be the same person.");
        }

        Course course = courseService.findById(courseId);

        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);
        subject.setCourse(course);

        if (studentIds != null && !studentIds.isEmpty()) {
            List<Student> students = studentService.findAll(studentIds);

            if (students.size() > 10) {
                throw new BusinessRuleException("A subject cannot have more than 10 students.");
            }

            subject.setStudents(students);
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

    public Subject update(Integer id, Subject updatedSubject, Integer mainProfessorId, Integer substituteProfessorId, List<Integer> studentIds) {
        Subject existingSubject = findById(id);

        existingSubject.setName(updatedSubject.getName());
        existingSubject.setDescription(updatedSubject.getDescription());

        if (mainProfessorId != null) {
            Professor mainProfessor = professorService.findById(mainProfessorId);
            existingSubject.setMainProfessor(mainProfessor);
        }
        if (substituteProfessorId != null) {
            Professor substituteProfessor = professorService.findById(substituteProfessorId);
            existingSubject.setSubstituteProfessor(substituteProfessor);
        }

        if (studentIds != null) {
            List<Student> students = studentService.findAll(studentIds);

            if (students.size() > 10) {
                throw new BusinessRuleException("A subject cannot have more than 10 students.");
            }

            existingSubject.setStudents(students);
        }

        return subjectRepository.save(existingSubject);
    }

    public void delete(Integer id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
    }
}
