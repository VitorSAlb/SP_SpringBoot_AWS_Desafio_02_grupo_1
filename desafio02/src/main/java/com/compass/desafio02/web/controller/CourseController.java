package com.compass.desafio02.web.controller;

import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.entities.Course;
import com.compass.desafio02.domain.services.CoordinatorService;
import com.compass.desafio02.domain.services.CourseService;
import com.compass.desafio02.web.dto.*;
import com.compass.desafio02.web.dto.course.CourseAddCooDto;
import com.compass.desafio02.web.dto.course.CourseCreateDto;
import com.compass.desafio02.web.dto.course.CourseResponseDto;
import com.compass.desafio02.web.dto.mapper.Mapper;
import com.compass.desafio02.web.dto.mapper.PageableMapper;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    // MUDAR OS COORDENADORES PARA DTO

    @Autowired
    private CourseService courseService;

    @Autowired
    private CoordinatorService coordinatorService;

    // Start Course ---------------------------------------------------------------------

    @GetMapping()
    public ResponseEntity<PageableDto> findAll(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        Page<Course> courses = courseService.findAll(pageable);

        List<CourseResponseDto> courseDtos = courses.getContent().stream()
                .map(Mapper::toCourseResponseDto).toList();
        Page<CourseResponseDto> courseDtosPage = new PageImpl<>(courseDtos, pageable, courses.getTotalElements());
        return ResponseEntity.ok(PageableMapper.toDto(courseDtosPage, CourseResponseDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> findById(@PathVariable Integer id) {
        Course course = courseService.findById(id);
        CourseResponseDto courseResponseDto = Mapper.toCourseResponseDto(course);
        return ResponseEntity.ok(courseResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CourseResponseDto> create(@RequestBody CourseCreateDto courseCreateDto) {
        String coordinatorEmail = courseCreateDto.getCoordinatorEmail();

        Course course = Mapper.toEntity(courseCreateDto, Course.class);

        if (coordinatorEmail != null) {
            Coordinator coo = coordinatorService.findByEmail(coordinatorEmail);
            course.setCoordinator(coo);
        }

        Course savedCourse = courseService.save(course);
        CourseResponseDto courseResponseDto = Mapper.toCourseResponseDto(savedCourse);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseResponseDto);
    }



    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> update(@PathVariable Integer id, @RequestBody CourseCreateDto courseUpdateDto) {
        Coordinator coordinator = coordinatorService.findByEmail(courseUpdateDto.getCoordinatorEmail());

        Course course = Mapper.toEntity(courseUpdateDto, Course.class);
        course.setCoordinator(coordinator);

        Course savedCourse = courseService.update(id, course);
        CourseResponseDto courseResponseDto = Mapper.toCourseResponseDto(savedCourse);
        return ResponseEntity.ok(courseResponseDto);
    }

    @PatchMapping("/coordinator")
    public ResponseEntity<CourseResponseDto> updateCoordinator(@RequestBody CourseAddCooDto dto) {
        log.info("email: " + dto.getCoordinatorEmail());
        log.info("name: " + dto.getName());
        Course updatedCourse = courseService.addCoordinatorToCourse(dto.getName(), dto.getCoordinatorEmail());
        return ResponseEntity.ok(Mapper.toCourseResponseDto(updatedCourse));
    }

    @PatchMapping("/remove/coordinator/{name}")
    public ResponseEntity<Void> removeCoordinator(@PathVariable String name) {
        courseService.removeCoordinatorFromCourse(name);
        return ResponseEntity.noContent().build();
    }

}
