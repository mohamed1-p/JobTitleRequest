package com.title.request.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.title.request.DTO.ResponsePage;
import com.title.request.models.Supervisor;
import com.title.request.services.SupervisorService;

@RestController
@RequestMapping("/api/supervisors")
public class SupervisorController {

    private final SupervisorService supervisorService;

    @Autowired
    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @GetMapping
    public ResponseEntity<ResponsePage<Supervisor>> getAllSupervisors(
        	@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
    		@RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
    	ResponsePage<Supervisor> supervisors = supervisorService.findAll(pageNo,pageSize);
        return new ResponseEntity<>(supervisors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supervisor> getSupervisorById(@PathVariable Long id) {
        Supervisor supervisor = supervisorService.findById(id);
        return new ResponseEntity<>(supervisor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Supervisor> createSupervisor(@RequestBody Supervisor supervisor) {
        Supervisor newSupervisor = supervisorService.createSupervisor(supervisor);
        return new ResponseEntity<>(newSupervisor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supervisor> updateSupervisor(@PathVariable Long id, @RequestBody Supervisor supervisorDetails) {
        Supervisor updatedSupervisor = supervisorService.updateSupervisor(id, supervisorDetails);
        return new ResponseEntity<>(updatedSupervisor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupervisor(@PathVariable Long id) {
        supervisorService.deleteSupervisor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
