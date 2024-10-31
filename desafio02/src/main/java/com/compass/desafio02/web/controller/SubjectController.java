package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.services.SubjectService;
import com.compass.desafio02.web.dto.subject.SubjectCreateDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;
import com.compass.desafio02.web.dto.mapper.SubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectResponseDto> createSubject(@Valid @RequestBody SubjectCreateDto dto) {
        Subject subject = SubjectMapper.toEntity(dto);
        Subject savedSubject = subjectService.save(
                subject,
                dto.getMainProfessor(),
                dto.getSubstituteProfessor(),
                dto.getCourseId(),
                dto.getStudentIds()
        );
        return ResponseEntity.status(201).body(SubjectMapper.toDto(savedSubject));
    }

    @GetMapping
    public ResponseEntity<Page<SubjectResponseDto>> getAllSubjects(Pageable pageable) {
        Page<Subject> subjects = subjectService.findAll(pageable);
        Page<SubjectResponseDto> subjectDtos = subjects.map(SubjectMapper::toDto);
        return ResponseEntity.ok(subjectDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<SubjectResponseDto> getSubjectById(@PathVariable Integer id) {
        Subject subject = subjectService.findById(id);
        return ResponseEntity.ok(SubjectMapper.toDto(subject));
    }

    @PutMapping("{id}")
    public ResponseEntity<SubjectResponseDto> updateSubject(@PathVariable Integer id, @Valid @RequestBody SubjectCreateDto dto) {
        Subject subject = SubjectMapper.toEntity(dto);
        Subject updatedSubject = subjectService.update(
                id,
                subject,
                dto.getMainProfessor(),
                dto.getSubstituteProfessor(),
                dto.getStudentIds()
        );
        return ResponseEntity.ok(SubjectMapper.toDto(updatedSubject));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Integer id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
