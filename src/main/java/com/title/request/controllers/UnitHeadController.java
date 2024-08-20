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
import com.title.request.models.UnitHead;
import com.title.request.services.UnitHeadService;

@RestController
@RequestMapping("/api/unit-heads")
public class UnitHeadController {

    private final UnitHeadService unitHeadService;

    @Autowired
    public UnitHeadController(UnitHeadService unitHeadService) {
        this.unitHeadService = unitHeadService;
    }

    @GetMapping
    public ResponseEntity<ResponsePage<UnitHead>> getAllUnitHeads(
    		@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
    		@RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
        ResponsePage<UnitHead> unitHeads = unitHeadService.findAll(pageNo,pageSize);
        return new ResponseEntity<>(unitHeads, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitHead> getUnitHeadById(@PathVariable Long id) {
        UnitHead unitHead = unitHeadService.findById(id);
        return new ResponseEntity<>(unitHead, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UnitHead> createUnitHead(@RequestBody UnitHead unitHead) {
        UnitHead newUnitHead = unitHeadService.createUnitHead(unitHead);
        return new ResponseEntity<>(newUnitHead, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitHead> updateUnitHead(@PathVariable Long id, @RequestBody UnitHead unitHeadDetails) {
        UnitHead updatedUnitHead = unitHeadService.updateUnitHead(id, unitHeadDetails);
        return new ResponseEntity<>(updatedUnitHead, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnitHead(@PathVariable Long id) {
        unitHeadService.deleteUnitHead(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
