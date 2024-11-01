package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.web.dto.course.CourseResponseDto;
import com.compass.desafio02.web.dto.subject.SubjectCreateDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;


import java.util.List;
import java.util.stream.Collectors;

public class SubjectMapper {

    public static Subject toEntity(SubjectCreateDto dto, Professor mainProfessor, Professor substituteProfessor, Course course, List<Student> students) {
        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setDescription(dto.getDescription());
        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);
        subject.setCourse(course);
        subject.setStudents(students);
        return subject;
    }

    public static SubjectResponseDto toDto(Subject subject) {
        SubjectResponseDto dto = new SubjectResponseDto();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setDescription(subject.getDescription());
        dto.setMainProfessor(ProfessorMapper.toDto(subject.getMainProfessor()));
        dto.setSubstituteProfessor(ProfessorMapper.toDto(subject.getSubstituteProfessor()));
        dto.setCourse(Mapper.toDto(subject.getCourse(), CourseResponseDto.class));
        dto.setStudents(subject.getStudents().stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }
}

