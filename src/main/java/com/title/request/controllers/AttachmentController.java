package com.title.request.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.title.request.DTO.AttachmentDto;
import com.title.request.DTO.ResponsePage;
import com.title.request.services.AttachmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/attachments")
public class AttachmentController {
	 	
	private final AttachmentService attachmentService;
	
	
	 @Autowired
	    public AttachmentController(AttachmentService attachmentService) {
	        this.attachmentService = attachmentService;
	      
	        
	    }
	 
	 
	 @GetMapping
	 public ResponseEntity<ResponsePage<AttachmentDto>> getAttachmentByRequest(
			 @RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
			 @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
	 	ResponsePage<AttachmentDto> attachmentDto = attachmentService.getAll(pageNo,pageSize);
		 
		 return ResponseEntity.ok(attachmentDto);
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
	 
	 
	 
	 @GetMapping("by-request")
	 public ResponseEntity<ResponsePage<AttachmentDto>> getAttachmentByRequest(@RequestParam("requestId") Long requestId,
			 @RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
			 @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
	 	ResponsePage<AttachmentDto> attachmentDto = attachmentService.findByRequestId(requestId,pageNo,pageSize);
		 
		 return ResponseEntity.ok(attachmentDto);
	 }
	 
	 
	 @GetMapping("/download/{attachmentId}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable Long attachmentId) throws IOException {
	        
	        Resource resource = attachmentService.getFile(attachmentId);

	        
	        Path filePath = Path.of(resource.getURI());
	        String contentType = Files.probeContentType(filePath);
	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }

	       
	        return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(contentType))
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	            .body(resource);
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

}
