package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.web.dto.professor.ProfessorCreateDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import org.modelmapper.ModelMapper;

public class ProfessorMapper {

    public static Professor toProfessor(ProfessorCreateDto professor) {
        return new ModelMapper().map(professor, Professor.class);
    }

    public static ProfessorResponseDto toProfessorResponseDto(Professor professor) {
        return new ModelMapper().map(professor, ProfessorResponseDto.class);
    }
}
