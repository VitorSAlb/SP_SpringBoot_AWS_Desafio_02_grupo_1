package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.web.dto.student.StudentCreateDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;
import org.modelmapper.ModelMapper;

public class StudentMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Student toEntity(StudentCreateDto dto) {
        return modelMapper.map(dto, Student.class);
    }

    public static StudentResponseDto toDto(Student student) {
        return modelMapper.map(student, StudentResponseDto.class);
    }
}
