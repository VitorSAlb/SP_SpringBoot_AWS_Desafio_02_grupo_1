package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Enrollment;
import com.compass.desafio02.web.dto.enrollment.EnrollmentResponseDto;

public class EnrollmentMapper {

    public static EnrollmentResponseDto toDto(Enrollment enrollment) {
        EnrollmentResponseDto dto = new EnrollmentResponseDto();
        dto.setId(enrollment.getId());
        dto.setStudent(StudentMapper.toDto(enrollment.getStudent()));
        dto.setCourse(CourseMapper.toDto(enrollment.getCourse()));
        return dto;
    }
}
