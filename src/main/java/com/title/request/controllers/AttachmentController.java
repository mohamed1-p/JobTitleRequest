package com.title.request.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.title.request.DTO.AttachmentDto;
import com.title.request.models.Attachment;
import com.title.request.services.AttachmentService;
import com.title.request.services.RequestService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/attachments")
public class AttachmentController {
	 	
	private final AttachmentService attachmentService;
	private final RequestService requestService;
	
	 @Autowired
	    public AttachmentController(AttachmentService attachmentService, 
	                                RequestService requestService
	                               ) {
	        this.attachmentService = attachmentService;
	        this.requestService = requestService;
	        
	    }
	 
	 
	 @PostMapping("/upload")
	    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
	                                             @RequestParam("requestId") Long requestId) {
	        try {
	        	attachmentService.saveFile(file, requestId);
	            return ResponseEntity.ok("File uploaded successfully: ");
	        } catch (IOException e) {
	            return ResponseEntity.badRequest().body("Could not upload file: " + e.getMessage());
	        }
	    }
	 
	 
	 
	 @GetMapping
	 public ResponseEntity<List<AttachmentDto>> getAttachmentByRequest(@RequestParam("requestId") Long requestId ) {
	 	System.out.println("in the controller method");
		 List<AttachmentDto> attachmentDto = attachmentService.findByRequestId(requestId);
		 
		 return ResponseEntity.ok(attachmentDto);
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

}
