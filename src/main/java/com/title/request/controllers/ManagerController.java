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

import com.title.request.DTO.JobDto;
import com.title.request.DTO.ResponsePage;
import com.title.request.models.Manager;
import com.title.request.services.ManagerService;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public ResponseEntity<ResponsePage<JobDto>> getAllManagers(
        	@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
    		@RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
        ResponsePage<JobDto> managers = managerService.findAll(pageNo,pageSize);
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Long id) {
        Manager manager = managerService.findById(id);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Manager> createManager(@RequestBody Manager manager) {
        Manager newManager = managerService.createManager(manager);
        return new ResponseEntity<>(newManager, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manager> updateManager(@PathVariable Long id, @RequestBody Manager managerDetails) {
        Manager updatedManager = managerService.updateManager(id, managerDetails);
        return new ResponseEntity<>(updatedManager, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}