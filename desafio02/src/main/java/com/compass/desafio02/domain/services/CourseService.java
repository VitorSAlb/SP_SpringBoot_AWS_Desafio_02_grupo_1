package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.repositories.CourseRepository;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

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

    public Course updateCoordinate(Integer id, Coordinator coordinator) {
        Course existingCourse = findById(id);
        existingCourse.setCoordinator(coordinator);
        existingCourse.setDescription(existingCourse.getDescription());
        return courseRepository.save(existingCourse);
    }
    // Testar troca de descrição ^^

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
}
