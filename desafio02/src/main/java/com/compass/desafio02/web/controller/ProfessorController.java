package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.ProfessorProjection;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import com.compass.desafio02.web.dto.mapper.ProfessorMapper;
import com.compass.desafio02.web.dto.professor.ProfessorCreateDto;
import com.compass.desafio02.web.dto.professor.ProfessorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.compass.desafio02.web.dto.mapper.ProfessorMapper.toProfessor;

@RestController
@RequestMapping("/api/v1/professors")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    // Professor Student ---------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<ProfessorResponseDto> createProfessor(@RequestBody ProfessorCreateDto professorDto) {
        Professor professor = ProfessorMapper.toProfessor(professorDto);
        professor.setRole(Role.ROLE_PROFESSOR);
        professorService.save(professor);
        return ResponseEntity.status(201).body(ProfessorMapper.toProfessorResponseDto(professor));
    }

    @GetMapping()
    public ResponseEntity<PageableDto> getAllProfessors(@PageableDefault(size = 5, sort = {"firstName"}) Pageable pageable) {
        Page<ProfessorProjection> professors = professorService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(professors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDto> getProfessorById(@PathVariable Integer id) {
        Professor professor = professorService.findById(id);
        return ResponseEntity.ok(ProfessorMapper.toProfessorResponseDto(professor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDto> updateProfessor(@PathVariable Integer id, @RequestBody Professor professor) {
        Professor updatedProfessor = professorService.update(id, professor);
        return ResponseEntity.ok(ProfessorMapper.toProfessorResponseDto(updatedProfessor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Integer id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // End Professor ---------------------------------------------------------------------
}
