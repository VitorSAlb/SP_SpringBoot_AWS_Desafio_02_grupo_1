package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.domain.repositories.StudentRepository;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.infrastructure.exceptions.DuplicateException;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import com.compass.desafio02.infrastructure.exceptions.BusinessRuleException;
import com.compass.desafio02.infrastructure.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.PropertyResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ProfessorService professorService;

    public Subject save(Subject subject) {
        Professor main = professorService.findByEmail(subject.getMainProfessor().getEmail());
        Professor sub = professorService.findByEmail(subject.getSubstituteProfessor().getEmail());

        if (subjectRepository.existsByName(subject.getName())) {
            throw new DuplicateException("This Subject already exists");
        }

        if (subject.getStudents() != null && subject.getStudents().size() > 10) {
            throw new BusinessRuleException("A subject cannot have more than 10 students.");
        }

        if (subject.getMainProfessor() == null) {
            throw new BusinessRuleException("A subject needs Main Professor");
        }

        if (subject.getMainProfessor() == subject.getSubstituteProfessor()) {
            throw new DuplicateException("The Main Professor e Substitute Professor cannot be the same person");
        }

        if (subject.getSubstituteProfessor() == null) {
            throw new BusinessRuleException("A subject needs Substitute Professor");
        }

        if(main.getCourse() == null) {
            throw new BusinessRuleException("The Main Professor must be registered in a course to associate with a subject. ");
        } else {
            subject.setCourse(main.getCourse());
        }

        if(sub.getCourse() == null) {
            throw new BusinessRuleException("The Substitute Professor must be registered in a course to associate with a subject. ");
        }

        subject.getMainProfessor().addSubjectHolder(subject);
        subject.getSubstituteProfessor().addSubjectSub(subject);

        try {
            return subjectRepository.save(subject);
        } catch (Exception e) {
            throw new DuplicateException(e.getMessage());
        }

    }

    public Subject findById(Integer id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
    }

    public Subject findByName(String name) {
        Subject subject = subjectRepository.findByName(name);
        if (subject == null) {
            throw new ResourceNotFoundException("Subject not found with name: " + name);
        }

        return subject;
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

    public void addCourse(Course course, Subject subject) {
        subject.setCourse(course);
        subjectRepository.save(subject);
    }

    public void removeCourse(Course course, Subject subject) {
        course.removeSubject(subject);
        subject.setCourse(null);
        subjectRepository.save(subject);
    }


}
