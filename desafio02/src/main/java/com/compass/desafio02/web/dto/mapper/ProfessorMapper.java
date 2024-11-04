package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.web.dto.professor.ProfessorCreateDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectHolderSubResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ProfessorMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Professor toEntity(ProfessorCreateDto dto) {
        return modelMapper.map(dto, Professor.class);
    }

    public static ProfessorResponseDto toDto(Professor professor) {
        ProfessorResponseDto dto = modelMapper.map(professor, ProfessorResponseDto.class);

        List<SubjectHolderSubResponseDto> mainSubjects = professor.getSubjectHolder()
                .stream()
                .map(ProfessorMapper::toSubjectDto)
                .toList();
        dto.setMainSubjects(mainSubjects);

        List<SubjectHolderSubResponseDto> substituteSubjects = professor.getSubjectSub()
                .stream()
                .map(ProfessorMapper::toSubjectDto)
                .toList();
        dto.setSubstituteSubjects(substituteSubjects);

        return dto;
    }

    private static SubjectHolderSubResponseDto toSubjectDto(Subject subject) {
        return new SubjectHolderSubResponseDto(subject.getName(), subject.getDescription());
    }


}
