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
    
    @Autowired
    public RequestService(RequestRepository requestRepository,
    		SupervisorRepository supervisorRepository ,UserRepository userRepository,
    		ManagerRepository managerRepository,
    		HeadDepartmentRepository headDepartmentRepository,
    		RequestStatusRepository statusRepository,UnitHeadRepository unitHeadRepository) {
        
    	this.requestRepository = requestRepository;
        this.managerRepository=managerRepository;
        this.supervisorRepository=supervisorRepository;
        this.headDepartmentRepository=headDepartmentRepository;
        this.unitHeadRepository=unitHeadRepository;
        this.statusRepository=statusRepository;
        this.userRepository=userRepository;
    }
    
    public List<ShowRequestDto> getAllRequests(){
    	
    	List<Request> requests = new ArrayList<>();
    	List<ShowRequestDto> requestDto = new ArrayList<>();
    	requests=requestRepository.findAll();
    	for(Request request : requests) {
    		ShowRequestDto tempRequesDto= new ShowRequestDto();
    		tempRequesDto.setRequestId(request.getId());
    		tempRequesDto.setFullName(request.getFullName());
    		tempRequesDto.setStatus(request.getRequestStatus().getStatusId());
    		tempRequesDto.setDepartment(request.getHeadDepartment().getName());
    		tempRequesDto.setSupervisor(request.getSupervisor().getName());
    		
    		requestDto.add(tempRequesDto);
    	}
    	return requestDto;
    }

    
    
    public RequestDTO createRequest(RequestDTO requestDto) {
    	System.out.println("in create request");
    	Request mappedRequest = mapToRequest(requestDto);
    	
    	//get the creator from the login session
    	System.out.println("fetching creator");
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth "+authentication);
    	String username = authentication.getName();
    	System.out.println("username "+ username);
        UserEntity creator = userRepository.findByUsername(username).
        		orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
    	System.out.println("createo "+creator.getName());
    	mappedRequest.setCreator(creator);
    	// add the attachment
    	mappedRequest.setRequestDate(LocalDateTime.now());
    	System.out.println("fetching status and setting it ");
    	mappedRequest.setRequestStatus(statusRepository.findById(0).orElseThrow());
    	
        requestRepository.save(mappedRequest);
         
         return requestDto;
    }

    public List<Request> findByCreator(UserEntity creator) {
        return requestRepository.findByCreator(creator);
    }

    public List<Request> findByStatus(int status) {
    	RequestStatus requestStatus = statusRepository.findById(status).orElseThrow();
        return requestRepository.findByRequestStatus(requestStatus);
    }

    public List<ShowRequestDto> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
         
         
         List<Request> requests = new ArrayList<>();
     	List<ShowRequestDto> requestDto = new ArrayList<>();
     	requests = requestRepository.findByRequestDateBetween(startDate, endDate);
     	for(Request request : requests) {
     		ShowRequestDto tempRequesDto= new ShowRequestDto();
     		tempRequesDto.setRequestId(request.getId());
     		tempRequesDto.setFullName(request.getFullName());
     		tempRequesDto.setStatus(request.getRequestStatus().getStatusId());
     		tempRequesDto.setDepartment(request.getHeadDepartment().getName());
     		tempRequesDto.setSupervisor(request.getSupervisor().getName());
     		
     		requestDto.add(tempRequesDto);
     	}
     	return requestDto;
    }

    @Transactional
    public Request updateRequestStatus(Long requestId, int status, String comments) {
    	//change the exception to a global exception
        Request request = requestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        RequestStatus state = statusRepository.findById(status).
        		orElseThrow(() -> new RuntimeException("State not found"));
        // set the user_admin who updated the request from the session login
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity admin = userRepository.findByUsername(username).
        		orElseThrow(() -> new UsernameNotFoundException("User not found"));
        request.setUserAdmin(admin);
        
        request.setRequestStatus(state);;
        request.setComments(comments);
        request.setActionDate(LocalDateTime.now());
        return requestRepository.save(request);
    }
    
    
    
    
    
    
    
    private Request mapToRequest(RequestDTO requestDto) {
    	Request mappedRequest = new Request();
    	
    	System.out.println("in map to request");
    	mappedRequest.setManager(managerRepository.findByCode(requestDto.getManagerCode()));
    	System.out.println("manager "+mappedRequest.getManager());
    	mappedRequest.setSupervisor(supervisorRepository.findByCode(requestDto.getSupervisorCode()));
    	System.out.println("superviser "+mappedRequest.getSupervisor());
    	mappedRequest.setHeadDepartment(headDepartmentRepository.findByCode(requestDto.getHeadDepartmentCode()));
    	System.out.println("dep "+mappedRequest.getHeadDepartment());
    	mappedRequest.setUnitHead(unitHeadRepository.findByCode(requestDto.getUnitHeadCode()));
    	System.out.println("head "+mappedRequest.getUnitHead());
    	mappedRequest.setFullName(requestDto.getFullName());
    	System.out.println("name "+mappedRequest.getFullName());
    	
    	return mappedRequest;
    }
}