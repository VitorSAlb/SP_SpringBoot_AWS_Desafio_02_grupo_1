package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.web.dto.StudentCreateDto;
import com.compass.desafio02.web.dto.StudentResponseDto;
import org.modelmapper.ModelMapper;

public class StudentMapper {

    public static Student toStudent(StudentCreateDto dto) {
        return new ModelMapper().map(dto, Student.class);
    }

    public static StudentResponseDto toDto(Student student) {
        return new ModelMapper().map(student, StudentResponseDto.class);
    }
}
