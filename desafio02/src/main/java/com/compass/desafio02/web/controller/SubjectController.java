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
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<Subject>> findAll(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        Page<Subject> subjects = subjectService.findAll(pageable);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> findById(@PathVariable Integer id) {
        Subject subject = subjectService.findById(id);
        return ResponseEntity.ok(subject);
    }

    @PostMapping
    public ResponseEntity<Subject> create(@RequestBody @Valid SubjectCreateDto subjectCreateDto) {
        // Buscar professores pelo ID
        Professor mainProfessor = professorService.findById(subjectCreateDto.getMainProfessor());
        Professor substituteProfessor = professorService.findById(subjectCreateDto.getSubstituteProfessor());

        // Buscar o curso pelo ID
        Course course = courseService.findById(subjectCreateDto.getCourseId());

        // Buscar os estudantes pelo email
        List<Student> students = subjectCreateDto.getStudentEmails().stream()
                .map(email -> studentService.findByEmail(email))
                .collect(Collectors.toList());

        // Criar o Subject e associar as entidades relacionadas
        Subject subject = new Subject();
        subject.setName(subjectCreateDto.getName());
        subject.setDescription(subjectCreateDto.getDescription());
        subject.setMainProfessor(mainProfessor);
        subject.setSubstituteProfessor(substituteProfessor);
        subject.setCourse(course);
        subject.setStudents(students);

        // Salvar e retornar o Subject
        Subject savedSubject = subjectService.save(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

