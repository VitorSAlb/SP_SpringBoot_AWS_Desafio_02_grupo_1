package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.services.SubjectService;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.subject.SubjectCreateDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;
import com.compass.desafio02.web.dto.PageableDto;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import com.compass.desafio02.web.dto.mapper.SubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        Page<Subject> subjects = subjectService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(subjects, SubjectResponseDto.class));
    }


    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> findById(@PathVariable Integer id) {
        Subject subject = subjectService.findById(id);
        return ResponseEntity.ok(Mapper.toDto(subject, SubjectResponseDto.class));
    }

    @PostMapping
    public ResponseEntity<SubjectResponseDto> create(@RequestBody @Valid SubjectCreateDto subjectCreateDto) {
        Professor mainProfessor = professorService.findById(subjectCreateDto.getMainProfessor());
        Professor substituteProfessor = professorService.findById(subjectCreateDto.getSubstituteProfessor());
        Course course = courseService.findById(subjectCreateDto.getCourseId());
        List<Student> students = subjectCreateDto.getStudentEmails().stream()
                .map(email -> studentService.findByEmail(email))
                .collect(Collectors.toList());

        Subject subject = SubjectMapper.toEntity(subjectCreateDto, mainProfessor, substituteProfessor, course, students);
        Subject savedSubject = subjectService.save(subject);
        return ResponseEntity.status(201).body(Mapper.toDto(savedSubject, SubjectResponseDto.class));
    }


    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> updateSubject(@PathVariable Integer id, @RequestBody SubjectCreateDto dto) {
        Professor mainProfessor = professorService.findById(dto.getMainProfessor());
        Professor substituteProfessor = professorService.findById(dto.getSubstituteProfessor());
        Course course = courseService.findById(dto.getCourseId());
        List<Student> students = dto.getStudentEmails().stream()
                .map(email -> studentService.findByEmail(email))
                .collect(Collectors.toList());

        Subject newSubject = SubjectMapper.toEntity(dto, mainProfessor, substituteProfessor, course, students);
        Subject updatedSubject = subjectService.update(id, newSubject);
        return ResponseEntity.ok(Mapper.toDto(updatedSubject, SubjectResponseDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
