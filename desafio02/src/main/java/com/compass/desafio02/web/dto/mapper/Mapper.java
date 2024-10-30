package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.web.dto.Coordinator.CoordinatorResponseDto;
import com.compass.desafio02.web.dto.Course.CourseResponseDto;
import com.compass.desafio02.web.subject.SubjectResponseDto;
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
        CoordinatorResponseDto coordinatorDto = coordinator != null
                ? modelMapper.map(coordinator, CoordinatorResponseDto.class)
                : null;

        List<SubjectResponseDto> subjectDtos = course.getSubject() != null && !course.getSubject().isEmpty()
                ? course.getSubject().stream()
                .map(subject -> modelMapper.map(subject, SubjectResponseDto.class))
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
