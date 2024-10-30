package com.compass.desafio02.domain.Controllers;

import com.compass.desafio02.domain.entities.Student;
import com.compass.desafio02.domain.services.StudentService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

public class StudentController {
    @Autowired
    private final StudentService studentService;
 
    
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
 
    @PostMapping
    public Student save(@RequestBody Student student) {
        return studentService.save(student);  // Cria um novo usuário
    }
    
    @GetMapping
    public Page<Student> findAll(Pageable pageable) {
        return studentService.findAll(pageable);
    }
 
    @GetMapping("/id")
    public Student findById(Integer id) {
        return studentService.findById(id); // TROCAR EXCESSÃO
        
    }
    @PutMapping("/{id}")
    public Student update(@PathVariable Integer id, @RequestBody Student newStudent) {
        return studentService.update(id, newStudent);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        studentService.delete(id);  // Chama o serviço para excluir o usuário pelo ID
    }
}
