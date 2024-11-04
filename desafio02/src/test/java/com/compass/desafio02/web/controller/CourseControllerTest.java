package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.infrastructure.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testFindById_Success() throws Exception {
        Course course = new Course();
        course.setId(1);
        course.setName("Mathematics");

        when(courseService.findById(anyInt())).thenReturn(course);

        mockMvc.perform(get("/api/v1/courses/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Mathematics")));
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testFindById_NotFound() throws Exception {
        when(courseService.findById(anyInt())).thenThrow(new ResourceNotFoundException("Course not found"));

        mockMvc.perform(get("/api/v1/courses/1"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testDeleteCourse_Success() throws Exception {
        Mockito.doNothing().when(courseService).delete(any());

        mockMvc.perform(delete("/api/v1/courses/Mathematics"))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "COORDINATOR")
    @Test
    public void testDeleteCourse_NotFound() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Course not found")).when(courseService).delete(any());

        mockMvc.perform(delete("/api/v1/courses/Mathematics"))
                .andExpect(status().isNotFound());
    }
}
