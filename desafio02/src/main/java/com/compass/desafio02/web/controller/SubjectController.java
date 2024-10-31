package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.entities.Subject;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.domain.services.ProfessorService;
import com.compass.desafio02.domain.services.StudentService;
import com.compass.desafio02.domain.services.SubjectService;
import com.compass.desafio02.domain.repositories.projection.StudentProjection;
import com.compass.desafio02.web.dto.subject.SubjectCreateDto;
import com.compass.desafio02.web.dto.subject.SubjectResponseDto;
import com.compass.desafio02.web.dto.mapper.SubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<SubjectResponseDto> createSubject(@Valid @RequestBody SubjectCreateDto dto, Pageable pageable) {
        Professor mainProfessor = professorService.findById(dto.getMainProfessor());
        Professor substituteProfessor = professorService.findById(dto.getSubstituteProfessor());
        Course course = courseService.findById(dto.getCourseId());

        Page<StudentProjection> studentPage = studentService.findAll(pageable);
        List<Student> students = studentPage.getContent().stream()
                .map(projection -> {
                    Student student = new Student();
                    student.setId(projection.getId());
                    student.setFirstName(projection.getFirstName());
                    student.setEmail(projection.getEmail());
                    return student;
                }).collect(Collectors.toList());

        Subject subject = SubjectMapper.toEntity(dto, mainProfessor, substituteProfessor, course, students);

        Subject savedSubject = subjectService.save(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(SubjectMapper.toDto(savedSubject));
    }

    @PutMapping("{id}")
    public ResponseEntity<SubjectResponseDto> updateSubject(
            @PathVariable Integer id, @Valid @RequestBody SubjectCreateDto dto, Pageable pageable) {

        Subject existingSubject = subjectService.findById(id);
        existingSubject.setName(dto.getName());
        existingSubject.setDescription(dto.getDescription());

        if (dto.getMainProfessor() != null) {
            Professor mainProfessor = professorService.findById(dto.getMainProfessor());
            existingSubject.setMainProfessor(mainProfessor);
        }

        if (dto.getSubstituteProfessor() != null) {
            Professor substituteProfessor = professorService.findById(dto.getSubstituteProfessor());
            existingSubject.setSubstituteProfessor(substituteProfessor);
        }

        if (dto.getCourseId() != null) {
            Course course = courseService.findById(dto.getCourseId());
            existingSubject.setCourse(course);
        }

        Page<StudentProjection> studentPage = studentService.findAll(pageable);
        List<Student> students = studentPage.getContent().stream()
                .map(projection -> {
                    Student student = new Student();
                    student.setId(projection.getId());
                    student.setFirstName(projection.getFirstName());
                    student.setEmail(projection.getEmail());
                    return student;
                }).collect(Collectors.toList());

        existingSubject.setStudents(students);

        Subject updatedSubject = subjectService.update(id, existingSubject);
        return ResponseEntity.ok(SubjectMapper.toDto(updatedSubject));
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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Integer id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
