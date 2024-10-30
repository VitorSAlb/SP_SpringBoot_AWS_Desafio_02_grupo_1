package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.services.ProfessorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professors")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<Professor> createProfessor(@RequestBody Professor professor) {
        Professor savedProfessor = professorService.save(professor);
        return ResponseEntity.status(201).body(savedProfessor);
    }

    @GetMapping()
    public ResponseEntity<Page<Professor>> getAllProfessors(Pageable pageable) {
        Page<Professor> professors = professorService.findAll(pageable);
        return ResponseEntity.ok(professors);
    }

    @GetMapping("{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Integer id) {
        Professor professor = professorService.findById(id);
        return ResponseEntity.ok(professor);
    }

    @PutMapping("{id}")
    public ResponseEntity<Professor> updateProfessor(@PathVariable Integer id, @RequestBody Professor professor) {
        Professor updatedProfessor = professorService.update(id, professor);
        return ResponseEntity.ok(updatedProfessor);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Integer id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
