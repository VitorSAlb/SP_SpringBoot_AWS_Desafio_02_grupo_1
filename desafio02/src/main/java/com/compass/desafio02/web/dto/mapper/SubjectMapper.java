package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.web.dto.subject.SubjectCreateDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;


import java.util.stream.Collectors;

public class SubjectMapper {

    public static Subject toEntity(SubjectCreateDto dto) {
        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setDescription(dto.getDescription());
        return subject;
    }

    public static SubjectResponseDto toDto(Subject subject) {
        SubjectResponseDto dto = new SubjectResponseDto();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setDescription(subject.getDescription());
        dto.setMainProfessor(ProfessorMapper.toDto(subject.getMainProfessor()));
        dto.setSubstituteProfessor(ProfessorMapper.toDto(subject.getSubstituteProfessor()));
        dto.setCourse(CourseMapper.toDto(subject.getCourse()));
        dto.setStudents(subject.getStudents().stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
