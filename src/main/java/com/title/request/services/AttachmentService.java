package com.title.request.services;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.title.request.DTO.AttachmentDto;
import com.title.request.DTO.ShowRequestDto;
import com.title.request.models.Attachment;
import com.title.request.models.AttachmentType;
import com.title.request.models.Request;
import com.title.request.repository.AttachmentRepository;
import com.title.request.repository.AttachmentTypeRepository;
import com.title.request.repository.RequestRepository;

@Service
public class AttachmentService {

	private final AttachmentRepository attachmentRepository;
	private final FileStorageService fileStorageService;
	private final AttachmentTypeRepository typeRepository;
	private final RequestRepository requestRepository;
	
	@Autowired
	public AttachmentService(AttachmentRepository attachmentRepository,
			FileStorageService fileStorageService,AttachmentTypeRepository typeRepository,
			RequestRepository requestRepository) {
		
		this.attachmentRepository=attachmentRepository;
		this.fileStorageService=fileStorageService;
		this.typeRepository=typeRepository;
		this.requestRepository=requestRepository;
	}
	
	
	
	@Transactional
	 public AttachmentDto saveFile(MultipartFile file, Long requestId)
			 throws IOException {
		 
		
	        String fileName = fileStorageService.storeFile(file);
	        Attachment attachment = new Attachment();
	        AttachmentType type = typeRepository.findByCode(fileStorageService.getFileExtension(fileName));
	
	        Request request = requestRepository.findById(requestId).orElseThrow(() ->
	        		new RuntimeException("No request by this Id"));
	        
	        attachment.setFileName(fileName);
	        attachment.setAttachmentType(type);
	        attachment.setFilePath(fileStorageService.getFilePath(fileName).toString());
	        attachment.setUploadDate(LocalDateTime.now());
	        attachment.setRequest(request);

	         attachmentRepository.save(attachment);
	         return new AttachmentDto(attachment.getId(),fileName,type.getName(),requestId);
	    }
	
	
	 public List<AttachmentDto> findByRequestId(Long requestId) {
		 
		  System.out.println("in find by id");
		  List<AttachmentDto> attachmentsDto =  new ArrayList<>();
		 List<Attachment> attachments = attachmentRepository.findByRequest(requestRepository.findById(requestId)
	        		.orElseThrow(()-> new RuntimeException("No request by this Id")));
	         
	       for(Attachment attachment : attachments) {
	    	   AttachmentDto dto = new AttachmentDto();
	    	   dto.setAttachmentId(attachment.getId());
	    	   dto.setFileName(attachment.getFileName());
	    	   dto.setAttachmentType(attachment.getAttachmentType().getName());
	    	   dto.setRequestId(requestId);
	    	   
	    	   attachmentsDto.add(dto);
	       }
	         return attachmentsDto;
	 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
