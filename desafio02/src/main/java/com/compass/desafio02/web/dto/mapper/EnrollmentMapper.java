package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.domain.entities.Enrollment;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import com.compass.desafio02.web.dto.course.CourseResponseDto;
import com.compass.desafio02.web.dto.enrollment.EnrollmentResponseDto;
import com.compass.desafio02.web.dto.student.StudentGResponseDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;

public class EnrollmentMapper {

    public static EnrollmentResponseDto toDto(Enrollment enrollment) {
        EnrollmentResponseDto dto = new EnrollmentResponseDto();
        dto.setId(enrollment.getId());
        dto.setStudent(Mapper.toDto(enrollment.getStudent(), StudentGResponseDto.class));
        dto.setCourse(Mapper.toDto(enrollment.getCourse(), CourseResponseDto.class));
        return dto;
    }
}
