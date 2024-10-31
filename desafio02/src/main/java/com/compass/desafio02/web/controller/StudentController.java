package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.enums.Role;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.student.StudentCreateDto;
import com.compass.desafio02.web.dto.student.StudentResponseDto;
import com.compass.desafio02.web.dto.UserPasswordDto;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import com.compass.desafio02.web.dto.mapper.StudentMapper;
import com.compass.desafio02.web.dto.student.StudentUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping()
    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, sort = {"firstName"}) Pageable pageable) {
        Page<StudentProjection> students = studentService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(students, StudentProjection.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> findById(@PathVariable Integer id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(Mapper.toDto(student, StudentResponseDto.class));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentResponseDto> findByEmail(@PathVariable String email) {
        Student student = studentService.findByEmail(email);
        return ResponseEntity.ok(Mapper.toDto(student, StudentResponseDto.class));
    }

    @PostMapping()
    public ResponseEntity<StudentResponseDto> create(@RequestBody StudentCreateDto dto) {
        Student student = StudentMapper.toEntity(dto);
        student.setRole(Role.ROLE_STUDENT);
        studentService.save(student);
        return ResponseEntity.status(201).body(Mapper.toDto(student, StudentResponseDto.class));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Integer id, @RequestBody StudentUpdateDto dto) {
        Student student = studentService.update(id, Mapper.toEntity(dto, Student.class));
        return ResponseEntity.ok(Mapper.toDto(student, StudentResponseDto.class));
    }

    @PatchMapping("/password/update/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Integer id, @RequestBody @Valid UserPasswordDto dto) {
        studentService.editPassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
