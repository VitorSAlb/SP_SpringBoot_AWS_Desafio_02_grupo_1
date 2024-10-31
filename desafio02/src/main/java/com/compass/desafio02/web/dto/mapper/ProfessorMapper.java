package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.web.dto.professor.ProfessorCreateDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import org.modelmapper.ModelMapper;

public class ProfessorMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Professor toEntity(ProfessorCreateDto dto) {
        return modelMapper.map(dto, Professor.class);
    }

    public static ProfessorResponseDto toDto(Professor professor) {
        return modelMapper.map(professor, ProfessorResponseDto.class);
    }
}
