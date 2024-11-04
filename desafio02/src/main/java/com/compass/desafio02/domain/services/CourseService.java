package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.*;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.infrastructure.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CoordinatorRepository coordinatorRepository;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CoordinatorService coordinatorService;

    public Course save(Course course) {
//        Coordinator coordinator = coordinatorRepository.findByEmail(course.getCoordinator().getEmail());
//
//        if (coordinator != null) {
//            if (!courseRepository.findByEmailCoordinator(coordinator.getEmail()).isEmpty()) {
//                throw new DuplicateException("Coordinator already enrolled in course");
//            }
//        }

        if (course.getName().isEmpty()) {
            throw new EmptyFieldException("Course name cannot be empty");
        }

        if (course.getDescription().isEmpty()) {
            throw new EmptyFieldException("Course description cannot be empty");
        }

        if (courseRepository.existsByName(course.getName())) {
            throw new DuplicateException("Course name already exists");
        }

        return courseRepository.save(course);
    }

    public Course findById(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    public Course findByName(String name) {
        Course course = courseRepository.findByName(name);
        if (course == null) {
            throw new ResourceNotFoundException("Course not found with name: " + name);
        }
        return course;
    }

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAllP(pageable);
    }

    public Course update(String name, Course newCourse) {
        Course existingCourse = findByName(name);

        if (newCourse.getName() == null || newCourse.getName().isEmpty()) {
            throw new EmptyFieldException("Updated course name cannot be empty");
        }

        if (courseRepository.existsByName(newCourse.getName())) {
            throw new InvalidCredentialsException("New name of course already registered");
        }

        existingCourse.setName(newCourse.getName());
        existingCourse.setDescription(newCourse.getDescription());

        return save(existingCourse);
    }

    public void delete(String name) {
        Course course = findByName(name);

        if (!courseRepository.existsById(course.getId())) {
            throw new ResourceNotFoundException("Course not found with name: " + course.getName());
        }
        courseRepository.deleteById(course.getId());
    }

    public Course addCoordinatorToCourse(String nameCourse, String email) {

        Course course = findByName(nameCourse);
        Coordinator coordinator = coordinatorRepository.findByEmail(email);

        if (coordinator == null || course == null) {
            throw new CoordinatorOrCourseNotFoundException("Coordinator or course not found.");
        }
        if (coordinator.getCourse() != null) {
            throw new CoordinatorAlreadyAssignedException("Coordinator is already assigned to another course.");
        }
        course.setCoordinator(coordinator);
        return courseRepository.save(course);
    }

    public void removeCoordinatorFromCourse(String nameCourse) {

        Course course = findByName(nameCourse);

        Coordinator coordinator = course.getCoordinator();
        if (coordinator != null) {
            coordinator.setCourse(null);
            coordinatorRepository.save(coordinator);
        }
        course.setCoordinator(null);
        courseRepository.save(course);
    }

    public void addSubjectToCourse(String nameCourse, String nameSubject) {
        Course course = findByName(nameCourse);
        Subject subject = subjectService.findByName(nameSubject);

        if (course.getSubjects().contains(subject)) {
            throw new DuplicateException("Subject already exists in course.");
        }

        if (course.getSubjects().size() >= 5) {
            throw new DuplicateException("Not can exists more than 5 subjects");
        }

        subjectService.addCourse(course, subject);
    }

    public void removeSubjectToCourse(String nameCourse, String nameSubject) {
        Course course = findByName(nameCourse);
        Subject subject = subjectService.findByName(nameSubject);

        if (!course.getSubjects().contains(subject)) {
            throw new DuplicateException("Subject not exists in course .");
        }

        subjectService.removeCourse(course, subject);
        course.removeSubject(subject);
    }

    public void addStudentToCourse(String nameCourse, String emailStudent) {
        Course course = findByName(nameCourse);
        Student student = studentService.findByEmail(emailStudent);

        if (student.getCourse() != null) {
            throw new DuplicateException("Student already exists in course.");
        }

        studentService.addCourse(course, student);
    }


}
