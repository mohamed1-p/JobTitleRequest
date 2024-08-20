package com.title.request.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.title.request.models.HeadDepartment;
import com.title.request.services.HeadDepartmentService;

@RestController
@RequestMapping("/api/head-departments")
public class HeadDepartmentController {

    private final HeadDepartmentService headDepartmentService;

    @Autowired
    public HeadDepartmentController(HeadDepartmentService headDepartmentService) {
        this.headDepartmentService = headDepartmentService;
    }

    @GetMapping
    public ResponseEntity<ResponsePage<HeadDepartment>> getAllHeadDepartments(
    		@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
			@RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
    	ResponsePage<HeadDepartment> headDepartments = headDepartmentService.findAll(pageNo,pageSize);
        return new ResponseEntity<>(headDepartments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeadDepartment> getHeadDepartmentById(@PathVariable Long id) {
        HeadDepartment headDepartment = headDepartmentService.findById(id);
        return new ResponseEntity<>(headDepartment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HeadDepartment> createHeadDepartment(@RequestBody HeadDepartment headDepartment) {
        HeadDepartment newHeadDepartment = headDepartmentService.createHeadDepartment(headDepartment);
        return new ResponseEntity<>(newHeadDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HeadDepartment> updateHeadDepartment(@PathVariable Long id,
    				@RequestBody HeadDepartment headDepartmentDetails) {
        HeadDepartment updatedHeadDepartment = headDepartmentService.updateHeadDepartment(id, headDepartmentDetails);
        return new ResponseEntity<>(updatedHeadDepartment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHeadDepartment(@PathVariable Long id) {
        headDepartmentService.deleteHeadDepartment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}