package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
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

    public Course save(Course course) {

        if (course.getName().isEmpty()) {
            throw new EmptyFieldException("Course name cannot be empty");
        }

        if (course.getDescription().isEmpty()) {
            throw new EmptyFieldException("Course description cannot be empty");
        }

        if (courseRepository.existsByName(course.getName())) {
            throw new DuplicateCourseException("Course name already exists");
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

    public Course update(Integer id, Course newCourse) {
        Course existingCourse = findById(id);

        existingCourse.setName(newCourse.getName());
        existingCourse.setDescription(newCourse.getDescription());

        return save(existingCourse);
    }

    public void delete(Integer id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
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

}
