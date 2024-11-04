package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.services.SubjectService;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.web.dto.subject.SubjectCreateDto;
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
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private ProfessorService professorService;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer mock-jwt-token";
    }


    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testFindByName_Success() throws Exception {
        Subject subject = new Subject();
        subject.setName("Mathematics");

        when(subjectService.findByName(anyString())).thenReturn(subject);

        mockMvc.perform(get("/api/v1/subjects/Mathematics")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Mathematics")));
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testCreateSubject_Success() throws Exception {
        SubjectCreateDto subjectCreateDto = new SubjectCreateDto();
        subjectCreateDto.setName("Physics");
        subjectCreateDto.setDescription("Physics Course Description");
        subjectCreateDto.setMainProfessorEmail("main.prof@test.com");
        subjectCreateDto.setSubstituteProfessorEmail("sub.prof@test.com");

        Professor mainProfessor = new Professor();
        mainProfessor.setEmail("main.prof@test.com");

        Professor subProfessor = new Professor();
        subProfessor.setEmail("sub.prof@test.com");

        when(professorService.findByEmail("main.prof@test.com")).thenReturn(mainProfessor);
        when(professorService.findByEmail("sub.prof@test.com")).thenReturn(subProfessor);

        mockMvc.perform(post("/api/v1/subjects")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDto)))
                .andExpect(status().isCreated());
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testUpdateSubject_Success() throws Exception {
        SubjectCreateDto subjectCreateDto = new SubjectCreateDto();
        subjectCreateDto.setName("Physics");
        subjectCreateDto.setDescription("Updated Physics Course Description");
        subjectCreateDto.setMainProfessorEmail("main.prof@test.com");
        subjectCreateDto.setSubstituteProfessorEmail("sub.prof@test.com");

        Subject updatedSubject = new Subject();
        updatedSubject.setName("Physics");

        when(subjectService.save(any(Subject.class))).thenReturn(updatedSubject);

        mockMvc.perform(put("/api/v1/subjects/update/Physics")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Physics")));
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testDeleteSubject_Success() throws Exception {
        Mockito.doNothing().when(subjectService).delete(anyString());

        mockMvc.perform(delete("/api/v1/subjects/Physics")
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }

}
