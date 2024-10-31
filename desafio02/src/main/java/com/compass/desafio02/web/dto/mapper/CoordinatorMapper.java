package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.web.dto.coordinator.CoordinatorCreateDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorResponseDto;
import org.modelmapper.ModelMapper;

public class CoordinatorMapper {

    public static Coordinator toCoodinator(CoordinatorCreateDto coordinator){
        return new ModelMapper().map(coordinator, Coordinator.class);
    }

    public static CoordinatorResponseDto toCoordinatorResponseDto(Coordinator coordinator){
        return new ModelMapper().map(coordinator, CoordinatorResponseDto.class);
    }
}
