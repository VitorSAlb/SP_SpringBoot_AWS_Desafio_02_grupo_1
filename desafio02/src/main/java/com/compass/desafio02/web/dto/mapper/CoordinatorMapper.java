package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.web.dto.coordinator.CoordinatorCreateDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorResponseDto;

public class CoordinatorMapper {

    public static Coordinator toEntity(CoordinatorCreateDto dto, Course course) {
        Coordinator coordinator = new Coordinator();
        coordinator.setFirstName(dto.getFirstName());
        coordinator.setLastName(dto.getLastName());
        coordinator.setEmail(dto.getEmail());
        coordinator.setBirthdate(dto.getBirthdate());
        coordinator.setPassword(dto.getPassword());
        coordinator.setCourse(course);
        return coordinator;
    }

    public static CoordinatorResponseDto toDto(Coordinator coordinator) {
        CoordinatorResponseDto dto = new CoordinatorResponseDto();
        dto.setId(coordinator.getId());
        dto.setFirstName(coordinator.getFirstName());
        dto.setLastName(coordinator.getLastName());
        dto.setEmail(coordinator.getEmail());
        return dto;
    }
}
