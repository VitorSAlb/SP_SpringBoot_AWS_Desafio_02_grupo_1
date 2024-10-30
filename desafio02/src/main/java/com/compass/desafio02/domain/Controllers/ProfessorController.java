package com.compass.desafio02.domain.Controllers;

import com.compass.desafio02.domain.entities.Professor;
import com.compass.desafio02.domain.services.ProfessorService;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;




public class ProfessorController {
    @Autowired
    private final ProfessorService professorService;
 
    
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }
 
    @PostMapping
    public Professor save(@RequestBody Professor professor) {
        return professorService.save(professor);  // Cria um novo usuário
    }
    
    @GetMapping
    public Page<Professor> findAll(Pageable pageable) {
        return professorService.findAll(pageable);
    }
 
    @GetMapping("/id")
    public Professor findById(Integer id) {
        return professorService.findById(id); // TROCAR EXCESSÃO
        
    }
    @PutMapping("/{id}")
    public Professor update(@PathVariable Integer id, @RequestBody Professor newProfessor) {
        return professorService.update(id, newProfessor);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        professorService.delete(id);  // Chama o serviço para excluir o usuário pelo ID
    }
}
