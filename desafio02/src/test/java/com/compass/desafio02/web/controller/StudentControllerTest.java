package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.domain.services.SubjectService;
import com.compass.desafio02.domain.repositories.SubjectRepository;
import com.compass.desafio02.web.dto.student.StudentUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @MockBean
    private SubjectRepository subjectRepository;

    @MockBean
    private SubjectService subjectService;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer mock-jwt-token";
    }


    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testFindById_Success() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setFirstName("John");

        when(studentService.findById(any(Integer.class))).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/1")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("John")));
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testFindByEmail_Success() throws Exception {
        Student student = new Student();
        student.setEmail("john@example.com");

        when(studentService.findByEmail(anyString())).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/email/john@example.com")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("john@example.com")));
    }


    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testUpdateStudent_Success() throws Exception {
        StudentUpdateDto studentUpdateDto = new StudentUpdateDto();
        studentUpdateDto.setFirstName("John Updated");
        studentUpdateDto.setLastName("Doe Updated");

        Student updatedStudent = new Student();
        updatedStudent.setFirstName("John Updated");

        when(studentService.update(anyString(), any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/api/v1/students/update/john@example.com")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("John Updated")));
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testDeleteStudent_Success() throws Exception {
        Mockito.doNothing().when(studentService).delete(anyString());

        mockMvc.perform(delete("/api/v1/students/john@example.com")
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testAddStudentToSubject_Success() throws Exception {
        Student student = new Student();
        student.setEmail("john@example.com");

        Subject subject = new Subject();
        subject.setName("Physics");

        when(studentService.findByEmail(anyString())).thenReturn(student);
        when(subjectRepository.findByName(anyString())).thenReturn(subject);

        mockMvc.perform(patch("/api/v1/students/add/subject")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentEmail\": \"john@example.com\", \"subjectName\": \"Physics\"}"))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testRemoveStudentFromSubject_Success() throws Exception {
        Student student = new Student();
        student.setEmail("john@example.com");

        Subject subject = new Subject();
        subject.setName("Physics");

        when(studentService.findByEmail(anyString())).thenReturn(student);
        when(subjectRepository.findByName(anyString())).thenReturn(subject);

        mockMvc.perform(patch("/api/v1/students/remove/subject")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentEmail\": \"john@example.com\", \"subjectName\": \"Physics\"}"))
                .andExpect(status().isNoContent());
    }
}
