package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import com.compass.desafio02.domain.services.CoordinatorService;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.StudentCreateDto;
import com.compass.desafio02.web.dto.StudentResponseDto;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import com.compass.desafio02.web.dto.mapper.StudentMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class Controller {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private CoordinatorService coordinatorService;

    // Start Student ---------------------------------------------------------------------

    @GetMapping("/students")
    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, sort = {"firstName"}) Pageable pageable) {
        Page<StudentProjection> students = studentService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(students));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponseDto> findById(@PathVariable Integer id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(StudentMapper.toDto(student));
    }

    @PostMapping("/students")
    public ResponseEntity<StudentResponseDto> create(@RequestBody @Valid StudentCreateDto dto) {
        Student student = StudentMapper.toStudent(dto);
        studentService.save(student);
        return ResponseEntity.status(201).body(StudentMapper.toDto(student));
    }

    @PutMapping("/students")


    // End Student ---------------------------------------------------------------------
}
