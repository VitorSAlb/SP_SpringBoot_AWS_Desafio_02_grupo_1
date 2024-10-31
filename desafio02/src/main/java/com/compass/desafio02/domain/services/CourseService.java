package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.repositories.CoordinatorRepository;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
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
        return courseRepository.save(course);
    }

    public Course findById(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    public Course findByName(String name) {
        try {
            return courseRepository.findByName(name);
        } catch (RuntimeException e) {
            throw new RuntimeException("Course not found with name: " + name); // Troca de exceção realizada ^^
        }
    }

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAllP(pageable);
    }

    public Course update(Integer id, Course newCourse) {
        Course existingCourse = findById(id);

        existingCourse.setName(newCourse.getName());
        existingCourse.setDescription(newCourse.getDescription());

        return courseRepository.save(existingCourse);
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
            throw new RuntimeException("Coordinator or course not founded.");
        }
        if (coordinator.getCourse() != null) {
            throw new RuntimeException("Coordinator cannot manage more than one course.");
        }
        course.setCoordinator(coordinator);
        return courseRepository.save(course);
    }

    public Course removeCoordinatorFromCourse(String nameCourse) {

        Course course = findByName(nameCourse);

        if (course == null) {
            throw new RuntimeException("Course not found.");
        }

        // Verifica se há um coordenador associado ao curso
        Coordinator coordinator = course.getCoordinator();
        if (coordinator != null) {
            coordinator.setCourse(null);
            coordinatorRepository.save(coordinator);
        }
        course.setCoordinator(null);
        return courseRepository.save(course);
    }

}
