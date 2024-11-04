package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.infrastructure.exceptions.user.UserNotFoundException;
import com.compass.desafio02.web.dto.professor.ProfessorCreateDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProfessorService professorService;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer mock-jwt-token";
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testGetProfessorByEmail_Success() throws Exception {
        Professor professor = new Professor();
        professor.setEmail("professor@test.com");
        professor.setFirstName("John");
        professor.setLastName("Doe");

        when(professorService.findByEmail(anyString())).thenReturn(professor);

        mockMvc.perform(get("/api/v1/professors/email/professor@test.com"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("professor@test.com")));
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testUpdateProfessor_Success() throws Exception {
        Professor professor = new Professor();
        professor.setEmail("professor@test.com");
        professor.setFirstName("John");
        professor.setLastName("Doe");

        when(professorService.update(anyString(), any(Professor.class))).thenReturn(professor);

        mockMvc.perform(put("/api/v1/professors/update/professor@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(professor)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("professor@test.com")));
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testDeleteProfessor_Success() throws Exception {
        Mockito.doNothing().when(professorService).delete(anyString());

        mockMvc.perform(delete("/api/v1/professors/professor@test.com"))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testDeleteProfessor_NotFound() throws Exception {
        Mockito.doThrow(new UserNotFoundException("Professor not found")).when(professorService).delete(anyString());

        mockMvc.perform(delete("/api/v1/professors/professor@test.com"))
                .andExpect(status().isNotFound());
    }
}
