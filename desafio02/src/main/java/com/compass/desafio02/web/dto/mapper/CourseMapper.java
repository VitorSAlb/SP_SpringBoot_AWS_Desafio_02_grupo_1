package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.web.dto.course.CourseResponseDto;

import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseResponseDto toDto(Course course) {
        return new CourseResponseDto(
                course.getId(),
                course.getName(),
                course.getDescription(),
                CoordinatorMapper.toCoordinatorResponseDto(course.getCoordinator()),
                course.getSubjects() != null
                        ? course.getSubjects().stream()
                        .map(SubjectMapper::toDto)
                        .collect(Collectors.toList())
                        : null
        );
    }
}
