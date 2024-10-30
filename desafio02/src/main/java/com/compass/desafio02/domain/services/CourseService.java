package com.compass.desafio02.domain.services;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.repositories.CourseRepository;
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
        return courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course not founded") // TROCAR EXCESSÃO
        );
    }

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAllP(pageable);
    }

    public Course updateCoordinate(Integer id, Coordinator coordinator) {
        Course existingCourse = findById(id);

        existingCourse.setCoordinator(coordinator);

        return courseRepository.save(existingCourse);
    }

    public Course update(Integer id, Course newCourse) {
        Course existingCourse = findById(id);

        existingCourse.setName(newCourse.getName());
        existingCourse.setDescription(newCourse.getDescription());

        return courseRepository.save(existingCourse);
    }

    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }
}
