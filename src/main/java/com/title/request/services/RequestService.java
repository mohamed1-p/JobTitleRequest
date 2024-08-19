package com.title.request.services;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.title.request.DTO.RequestDTO;
import com.title.request.DTO.ShowRequestDto;
import com.title.request.models.Request;
import com.title.request.models.RequestStatus;
import com.title.request.models.UserEntity;
import com.title.request.repository.AttachmentRepository;
import com.title.request.repository.HeadDepartmentRepository;
import com.title.request.repository.ManagerRepository;
import com.title.request.repository.RequestRepository;
import com.title.request.repository.RequestStatusRepository;
import com.title.request.repository.SupervisorRepository;
import com.title.request.repository.UnitHeadRepository;
import com.title.request.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RequestService {
	
    private final RequestRepository requestRepository;
    private final ManagerRepository managerRepository;
    private final SupervisorRepository supervisorRepository;
    private final HeadDepartmentRepository headDepartmentRepository;
    private final UnitHeadRepository unitHeadRepository;
    private final RequestStatusRepository statusRepository;
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    
    @Autowired
    public RequestService(RequestRepository requestRepository,
    		SupervisorRepository supervisorRepository ,UserRepository userRepository,
    		ManagerRepository managerRepository,
    		HeadDepartmentRepository headDepartmentRepository,
    		RequestStatusRepository statusRepository,
    		AttachmentRepository attachmentRepository,UnitHeadRepository unitHeadRepository) {
        
    	this.requestRepository = requestRepository;
        this.managerRepository=managerRepository;
        this.supervisorRepository=supervisorRepository;
        this.headDepartmentRepository=headDepartmentRepository;
        this.unitHeadRepository=unitHeadRepository;
        this.statusRepository=statusRepository;
        this.userRepository=userRepository;
        this.attachmentRepository=attachmentRepository;
    }
    
    public List<ShowRequestDto> getAllRequests(){
    	
    	List<Request> requests = new ArrayList<>();
    	List<ShowRequestDto> requestDto = new ArrayList<>();
    	requests=requestRepository.findAll();
    	for(Request request : requests) {
    		ShowRequestDto tempRequesDto= new ShowRequestDto();
    		tempRequesDto.setRequestId(request.getId());
    		tempRequesDto.setFullName(request.getFullName());
    		tempRequesDto.setStatus(request.getRequestStatus().getStatusName());
    		tempRequesDto.setDepartment(request.getHeadDepartment().getName());
    		tempRequesDto.setSupervisor(request.getSupervisor().getName());
    		
    		requestDto.add(tempRequesDto);
    	}
    	return requestDto;
    }

    
    
    public RequestDTO createRequest(RequestDTO requestDto) {
    	
    	Request mappedRequest = mapToRequest(requestDto);
    	
    	
    	
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
    	String username = authentication.getName();
    	
        UserEntity creator = userRepository.findByUsername(username).
        		orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
    	
    	mappedRequest.setCreator(creator);
    	
    	mappedRequest.setRequestDate(LocalDateTime.now());
    	mappedRequest.setRequestStatus(statusRepository.findById(0).orElseThrow());
    	
        requestRepository.save(mappedRequest);
        mappedRequest.setAttachments(attachmentRepository.findByRequest(mappedRequest));
        return requestDto;
    }

    
    
    public List<ShowRequestDto> findByCreator(Long creatorId) {
    	UserEntity creator = userRepository.findById(creatorId).
    			orElseThrow(()-> new RuntimeException("No user exist by this id"));
         
        List<ShowRequestDto> requestDto = new ArrayList<>();
        List<Request> requests = new ArrayList<>();
        requests = requestRepository.findByCreator(creator);
        for(Request request:requests){
       	 ShowRequestDto dto = new ShowRequestDto();
       	 dto.setRequestId(request.getId());
       	 dto.setFullName(request.getFullName());
       	 dto.setStatus(request.getRequestStatus().getStatusName());
       	 dto.setDepartment(request.getHeadDepartment().getName());
       	 dto.setSupervisor(request.getSupervisor().getName());
       	 
       	 requestDto.add(dto);
        }
        
        
        return requestDto;
    }
    
    

    public List<ShowRequestDto> findByStatus(int status) {
    	RequestStatus requestStatus = statusRepository.findById(status).orElseThrow();
    	
    	List<ShowRequestDto> requestDto = new ArrayList<>();
         List<Request> requests = new ArrayList<>();
         requests = requestRepository.findByRequestStatus(requestStatus);
         
         for(Request request:requests){
        	 ShowRequestDto dto = new ShowRequestDto();
        	 dto.setRequestId(request.getId());
        	 dto.setFullName(request.getFullName());
        	 dto.setStatus(request.getRequestStatus().getStatusName());
        	 dto.setDepartment(request.getHeadDepartment().getName());
        	 dto.setSupervisor(request.getSupervisor().getName());
        	 
        	 requestDto.add(dto);
         }
         
         
         return requestDto;
    }

    public List<ShowRequestDto> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
         
         
         List<Request> requests = new ArrayList<>();
     	List<ShowRequestDto> requestDto = new ArrayList<>();
     	requests = requestRepository.findByRequestDateBetween(startDate, endDate);
     	for(Request request : requests) {
     		ShowRequestDto tempRequesDto= new ShowRequestDto();
     		tempRequesDto.setRequestId(request.getId());
     		tempRequesDto.setFullName(request.getFullName());
     		tempRequesDto.setStatus(request.getRequestStatus().getStatusName());
     		tempRequesDto.setDepartment(request.getHeadDepartment().getName());
     		tempRequesDto.setSupervisor(request.getSupervisor().getName());
     		
     		requestDto.add(tempRequesDto);
     	}
     	return requestDto;
    }

    @Transactional
    public ShowRequestDto updateRequestStatus(Long requestId, int status, String comments) {
    	
    	ShowRequestDto requestDto = new ShowRequestDto();
    	
        Request request = requestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        RequestStatus state = statusRepository.findById(status).
        		orElseThrow(() -> new RuntimeException("State not found"));
 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity admin = userRepository.findByUsername(username).
        		orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        request.setUserAdmin(admin);
        request.setRequestStatus(state);;
        request.setComments(comments);
        request.setActionDate(LocalDateTime.now());

        requestRepository.save(request);
        
        requestDto.setRequestId(request.getId());
        requestDto.setFullName(request.getFullName());
        requestDto.setStatus(request.getRequestStatus().getStatusName());
        requestDto.setDepartment(request.getHeadDepartment().getName());
        requestDto.setSupervisor(request.getSupervisor().getName());
 		
        return requestDto;
    }
    
    
    
    
    
    
    
    private Request mapToRequest(RequestDTO requestDto) {
    	Request mappedRequest = new Request();
    	
    	mappedRequest.setManager(managerRepository.findByCode(requestDto.getManagerCode()));
    	mappedRequest.setSupervisor(supervisorRepository.findByCode(requestDto.getSupervisorCode()));
    	mappedRequest.setHeadDepartment(headDepartmentRepository.findByCode(requestDto.getHeadDepartmentCode()));
    	mappedRequest.setUnitHead(unitHeadRepository.findByCode(requestDto.getUnitHeadCode()));
    	mappedRequest.setFullName(requestDto.getFullName());
    	
    	return mappedRequest;
    }
}