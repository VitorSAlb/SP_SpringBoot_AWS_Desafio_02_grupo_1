package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.web.dto.coordinator.CoordinatorNoCourseResponseDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorResponseDto;
import com.compass.desafio02.web.dto.course.CourseResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseNoCourseDto;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

public class Mapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    // DTO para entidade
    public static <D, E> E toEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    // entidade para DTO
    public static <E, D> D toDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public static CourseResponseDto toCourseResponseDto(Course course) {
        Coordinator coordinator = course.getCoordinator();
        CoordinatorNoCourseResponseDto coordinatorDto = coordinator != null
                ? modelMapper.map(coordinator, CoordinatorNoCourseResponseDto.class)
                : null;

        List<SubjectResponseNoCourseDto> subjectDtos = course.getSubjects() != null && !course.getSubjects().isEmpty()
                ? course.getSubjects().stream()
                .map(subject -> modelMapper.map(subject, SubjectResponseNoCourseDto.class))
                .toList()
                : Collections.emptyList();

        return new CourseResponseDto(
                course.getId(),
                course.getName(),
                course.getDescription(),
                coordinatorDto,
                subjectDtos
        );
    }

}
