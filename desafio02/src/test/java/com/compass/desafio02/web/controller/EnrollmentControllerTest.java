package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Course;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.domain.services.EnrollmentService;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.web.dto.enrollment.enrollmentCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnrollmentService enrollmentService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private CourseService courseService;

    private String token;


    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testCreateEnrollment_Success() throws Exception {
        enrollmentCreateDto enrollmentCreateDto = new enrollmentCreateDto();
        enrollmentCreateDto.setStudentEmail("john@example.com");
        enrollmentCreateDto.setCourseName("Physics");

        Student student = new Student();
        student.setEmail("john@example.com");

        Course course = new Course();
        course.setName("Physics");

        when(studentService.findByEmail(anyString())).thenReturn(student);
        when(courseService.findByName(anyString())).thenReturn(course);

        mockMvc.perform(post("/api/v1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollmentCreateDto)))
                .andExpect(status().isCreated());
    }


    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testDeleteEnrollment_Success() throws Exception {
        Mockito.doNothing().when(enrollmentService).deleteEnrollment(anyInt());

        mockMvc.perform(delete("/api/v1/enrollments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }



}
