package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.domain.repositories.StudentRepository;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import com.compass.desafio02.infrastructure.exceptions.BusinessRuleException;
import com.compass.desafio02.infrastructure.exceptions.user.UserNotFoundException;
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
    @Autowired
    private CourseRepository courseRepository;

    public Subject save(Subject subject) {

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
