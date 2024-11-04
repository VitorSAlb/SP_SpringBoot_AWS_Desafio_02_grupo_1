package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.*;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.domain.repositories.StudentRepository;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.infrastructure.exceptions.*;
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
    @Autowired
    private StudentRepository studentRepository;

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

    public Subject update(String name, Subject updatedSubject) {
        Subject existingSubject = findByName(name);

        existingSubject.setName(updatedSubject.getName());
        existingSubject.setDescription(updatedSubject.getDescription());

        Professor mainProfessor = professorService.findByEmail(updatedSubject.getMainProfessor().getEmail());
        Professor subProfessor = professorService.findByEmail(updatedSubject.getSubstituteProfessor().getEmail());


        if(mainProfessor.getCourse() == null) {
            throw new BusinessRuleException("The Main Professor must be registered in a course to associate with a subject. ");
        } else {
            existingSubject.setCourse(mainProfessor.getCourse());
        }

        if(subProfessor.getCourse() == null) {
            throw new BusinessRuleException("The Substitute Professor must be registered in a course to associate with a subject. ");
        }

        existingSubject.setMainProfessor(mainProfessor);
        existingSubject.setSubstituteProfessor(subProfessor);

        return subjectRepository.save(existingSubject);
    }

    public void delete(String name) {
        Subject subject = findByName(name);

        if (!subjectRepository.existsById(subject.getId())) {
            throw new BusinessRuleException("Subject not found with name: " + subject.getName());
        }
        subjectRepository.deleteById(subject.getId());
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

    public void addStudent(Student student, Subject subject) {

        if (student.getCourse() == null) {
            throw new BusinessRuleException("Student must be enrolled in a course to enter a subject");
        }

        if (!student.getCourse().equals(subject.getCourse())) {
            throw new BusinessRuleException("Student must be enrolled in the course to take the subject");
        }

        if (subject.getStudents().size() > 10) {
            throw new BusinessRuleException("A subject cannot have more than 10 students.");
        }

        student.addSubject(subject);
        studentRepository.save(student);
    }

    public void removeStudent(Student student, Subject subject) {

        student.removeSubject(subject);
        studentRepository.save(student);
    }


}
