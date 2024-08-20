package com.title.request.services;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.title.request.DTO.AttachmentDto;
import com.title.request.DTO.ResponsePage;
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
	
	
	 public ResponsePage<AttachmentDto> findByRequestId(Long requestId,int pageNo,int pageSize) {
		 
		  

		 Pageable pageable = PageRequest.of(pageNo, pageSize);
		 Page<Attachment> attachmentsPage = attachmentRepository.findByRequest(requestRepository.findById(requestId)
	        		.orElseThrow(()-> new RuntimeException("No request by this Id")),pageable);
		 List<Attachment> attachments = attachmentsPage.getContent();
		 
		 List<AttachmentDto> attachmentsDto = attachments.parallelStream()
				    .map(this::mapAttachmentToDto)
				    .collect(Collectors.toList());
	     
		 ResponsePage<AttachmentDto> content = mapAttachmentToPageObject(attachmentsPage, attachmentsDto);
	      return content;
	 }
	
	
	
	
	
	private AttachmentDto mapAttachmentToDto(Attachment attachment) {
	   AttachmentDto dto = new AttachmentDto();
  	   dto.setAttachmentId(attachment.getId());
  	   dto.setFileName(attachment.getFileName());
  	   dto.setAttachmentType(attachment.getAttachmentType().getName());
  	   dto.setRequestId(attachment.getId());
  	   
  	   return dto;
	}
	
	
	
	private ResponsePage<AttachmentDto> mapAttachmentToPageObject(Page<Attachment> attachmentsPage,
			 List<AttachmentDto> attachmentsDto){
		
		ResponsePage<AttachmentDto> content = new ResponsePage<>();
		
		content.setContent(attachmentsDto);
		content.setPage(attachmentsPage.getNumber());
      	content.setSize(attachmentsPage.getSize());
      	content.setTotalElements(attachmentsPage.getTotalElements());
      	content.setTotalpages(attachmentsPage.getTotalPages());
      	content.setLast(attachmentsPage.isLast());
	
		return content;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
