package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.services.CoordinatorService;
import com.compass.desafio02.web.dto.UserPasswordDto;
import com.compass.desafio02.web.dto.coordinator.CoordinatorCreateDto;
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

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoordinatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoordinatorService coordinatorService;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer mock-jwt-token";
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testCreateCoordinator_Success() throws Exception {
        CoordinatorCreateDto coordinatorCreateDto = new CoordinatorCreateDto();
        coordinatorCreateDto.setFirstName("Alice");
        coordinatorCreateDto.setLastName("Smith");
        coordinatorCreateDto.setEmail("alice.smith@test.com");
        coordinatorCreateDto.setPassword("ValidPass1!");
        coordinatorCreateDto.setBirthdate(LocalDate.parse("1990-01-01"));

        mockMvc.perform(post("/api/v1/coordinators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coordinatorCreateDto)))
                .andExpect(status().isCreated());
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testGetCoordinatorById_Success() throws Exception {
        Coordinator coordinator = new Coordinator();
        coordinator.setId(1);
        coordinator.setEmail("coordinator@test.com");
        coordinator.setFirstName("Alice");
        coordinator.setLastName("Smith");

        when(coordinatorService.findById(1)).thenReturn(coordinator);

        mockMvc.perform(get("/api/v1/coordinators/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("coordinator@test.com")));
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testGetCoordinatorByEmail_Success() throws Exception {
        UserPasswordDto passwordDto = new UserPasswordDto();
        passwordDto.setCurrentPassword("ValidPass1!");
        passwordDto.setNewPassword("ValidPass2!");
        passwordDto.setConfirmPassword("ValidPass2!");

        mockMvc.perform(patch("/api/v1/coordinators/password/update/coordinator@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto)))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testUpdateCoordinator_Success() throws Exception {
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("coordinator@test.com");
        coordinator.setFirstName("Alice");
        coordinator.setLastName("Smith");

        when(coordinatorService.update(anyString(), any(Coordinator.class))).thenReturn(coordinator);

        mockMvc.perform(put("/api/v1/coordinators/update/coordinator@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coordinator)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("coordinator@test.com")));
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testUpdatePassword_Success() throws Exception {
        UserPasswordDto passwordDto = new UserPasswordDto();
        passwordDto.setCurrentPassword("ValidPass1!");
        passwordDto.setNewPassword("ValidPass2!");
        passwordDto.setConfirmPassword("ValidPass2!");

        mockMvc.perform(patch("/api/v1/coordinators/password/update/coordinator@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto)))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testDeleteCoordinator_Success() throws Exception {
        Mockito.doNothing().when(coordinatorService).delete(anyString());

        mockMvc.perform(delete("/api/v1/coordinators/coordinator@test.com"))
                .andExpect(status().isNoContent());
    }
}
