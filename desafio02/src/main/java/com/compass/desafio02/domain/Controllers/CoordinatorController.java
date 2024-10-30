package com.compass.desafio02.domain.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.compass.desafio02.domain.entities.Coordinator;
import com.compass.desafio02.domain.services.CoordinatorService;


public class CoordinatorController {
    
    @Autowired
   private final CoordinatorService coordinatorService;

   
   public CoordinatorController(CoordinatorService coordinatorService) {
       this.coordinatorService = coordinatorService;
   }

   @PostMapping
   public Coordinator save(@RequestBody Coordinator coordinator) {
       return coordinatorService.save(coordinator);  // Cria um novo usuário
   }
   
   @GetMapping
   public Page<Coordinator> findAll(Pageable pageable) {
       return coordinatorService.findAll(pageable);
   }

   @GetMapping("/id")
   public Coordinator findById(Integer id) {
       return coordinatorService.findById(id); // TROCAR EXCESSÃO
       
   }
   @PutMapping("/{id}")
   public Coordinator update(@PathVariable Integer id, @RequestBody Coordinator newCoordinator) {
       return coordinatorService.update(id, newCoordinator);
   }
   
   @DeleteMapping("/{id}")
   public void delete(@PathVariable Integer id) {
       coordinatorService.delete(id);  // Chama o serviço para excluir o usuário pelo ID
   }
}