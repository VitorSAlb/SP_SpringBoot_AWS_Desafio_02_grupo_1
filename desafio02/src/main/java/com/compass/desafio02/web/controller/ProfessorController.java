package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.ProfessorProjection;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.UserPasswordDto;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import com.compass.desafio02.web.dto.mapper.ProfessorMapper;
import com.compass.desafio02.web.dto.professor.ProfessorCreateDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/professors")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorResponseDto> createProfessor(@RequestBody ProfessorCreateDto professorDto) {
        Professor professor = ProfessorMapper.toEntity(professorDto);
        professor.setRole(Role.ROLE_PROFESSOR);
        professorService.save(professor);
        return ResponseEntity.status(201).body(ProfessorMapper.toDto(professor));
    }

    @GetMapping()
    public ResponseEntity<PageableDto> getAllProfessors(@PageableDefault(size = 5, sort = {"firstName"}) Pageable pageable) {
        Page<ProfessorProjection> professors = professorService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(professors, Professor.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDto> getProfessorById(@PathVariable Integer id) {
        Professor professor = professorService.findById(id);
        return ResponseEntity.ok(ProfessorMapper.toDto(professor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDto> updateProfessor(@PathVariable Integer id, @RequestBody Professor professor) {
        Professor updatedProfessor = professorService.update(id, professor);
        return ResponseEntity.ok(ProfessorMapper.toDto(updatedProfessor));
    }

    @PatchMapping("/password/update/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Integer id, @RequestBody @Valid UserPasswordDto dto) {
        professorService.editPassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Integer id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
