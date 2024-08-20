package com.title.request.services;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.title.request.DTO.RequestDTO;
import com.title.request.DTO.ResponsePage;
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
    
    
    /**
     * 
     * @param pageNo
     * @param pageSize
     * @return All the requests mapped to a DTO object
     */
    public ResponsePage<ShowRequestDto> getAllRequests(int pageNo, int pageSize){
    	
    	//object to pass to the find all method
    	Pageable pageable = PageRequest.of(pageNo, pageSize);
    	Page<Request> requestsPage = requestRepository.findAll(pageable);
    	List<Request> requests = requestsPage.getContent();
    	
    	
    	 //map to DTO to make the response more readable
     	List<ShowRequestDto> requestDto = requests.parallelStream()
     		    .map(this::mapRequestToDto)
     		    .collect(Collectors.toList());
     	
     	//set the pageable content to return it to the controller
     	ResponsePage<ShowRequestDto> content = mapRequestToPageOject(requestsPage,requestDto);
        return content;
    }

    
    /**
     * 
     * @param requestDto
     * @return	return the created DTO object containing the data saved in the DataBase
     */
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

    
    /**
     * 
     * @param creatorId
     * @param pageNo
     * @param pageSize
     * @return	Return a list of Requests mapped to a DTO object where the Creator 
     * 			of the Request is equal to creatorId
     */
    public ResponsePage<ShowRequestDto> findByCreator(Long creatorId,int pageNo,int pageSize) {
    	
    	
    	
    	
    	UserEntity creator = userRepository.findById(creatorId).
    			orElseThrow(()-> new RuntimeException("No user exist by this id"));
        
    	//handling the page object
    	Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Request> requestsPage = requestRepository.findByCreator(creator,pageable);
        List<Request> requests = requestsPage.getContent();
        
        //map to DTO to make the response more readable
     	List<ShowRequestDto> requestDto = requests.parallelStream()
     		    .map(this::mapRequestToDto)
     		    .collect(Collectors.toList());
     	
     	//set the pageable content to return it to the controller
     	ResponsePage<ShowRequestDto> content = mapRequestToPageOject(requestsPage,requestDto);
        return content;
    }
    
    
/**
 * 
 * @param status
 * @param pageNo
 * @param pageSize
 * @return Return a list of Requests mapped to a DTO object where the Status 
 * 		   of the Request is equal to status
 */
    public ResponsePage<ShowRequestDto> findByStatus(int status, int pageNo,int pageSize) {
    	RequestStatus requestStatus = statusRepository.findById(status).orElseThrow();
    	
    	//handling the page object
    	Pageable pageable = PageRequest.of(pageNo, pageSize);
    	Page<Request> requestsPage = requestRepository.findByRequestStatus(requestStatus,pageable);
        List<Request> requests = requestsPage.getContent();
        
        
        //map to DTO to make the response more readable
     	List<ShowRequestDto> requestDto = requests.parallelStream()
     		    .map(this::mapRequestToDto)
     		    .collect(Collectors.toList());
     	
     	//set the pageable content to return it to the controller
     	ResponsePage<ShowRequestDto> content = mapRequestToPageOject(requestsPage,requestDto);
        return content;
    }

    
    /**
     * 
     * @param startDate
     * @param endDate
     * @param pageNo
     * @param pageSize
     * @return Return a list of Requests mapped to a DTO object where the creation 
     * 		   date of the Request is between  startDate and endDate
     */
    public ResponsePage<ShowRequestDto> findByDateRange(LocalDateTime startDate, LocalDateTime endDate,
    		int pageNo,int pageSize) {
         
         
    	//handling the page object
    	Pageable pageable = PageRequest.of(pageNo, pageSize);
    	Page<Request> requestsPage = requestRepository.findByRequestDateBetween(startDate, endDate,pageable);
        List<Request> requests = requestsPage.getContent();
        
        
        //map to DTO to make the response more readable
     	List<ShowRequestDto> requestDto = requests.parallelStream()
     		    .map(this::mapRequestToDto)
     		    .collect(Collectors.toList());
     	
     	//set the pageable content to return it to the controller
     	ResponsePage<ShowRequestDto> content = mapRequestToPageOject(requestsPage,requestDto);
        return content;
    }

    /**
     * 
     * @param requestId
     * @param status
     * @param comments
     * @return Returns a request object mapped to a DTO after the UserAdmin update it's
     * 		   statu and add comments 
     */
    @Transactional
    public ShowRequestDto updateRequestStatus(Long requestId, int status, String comments) {
    	
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
        
        ShowRequestDto requestDto =  mapRequestToDto(request);
        return requestDto;
    }
    
    //login using token not session
    // service and manager make service 
    // pagenigtion for the search
    //extract the jason file from postman
    //
    
    
    
    private ResponsePage<ShowRequestDto> mapRequestToPageOject(Page<Request> requestsPage,
    		List<ShowRequestDto> requestDto ){
    	
    	ResponsePage<ShowRequestDto> content = new ResponsePage<>();
    	content.setContent(requestDto);
      	content.setPage(requestsPage.getNumber());
      	content.setSize(requestsPage.getSize());
      	content.setTotalElements(requestsPage.getTotalElements());
      	content.setTotalpages(requestsPage.getTotalPages());
      	content.setLast(requestsPage.isLast());
      	
    	return content;
    }
    
    
    private ShowRequestDto mapRequestToDto(Request request) {
    	
    	ShowRequestDto tempRequesDto= new ShowRequestDto();
 		tempRequesDto.setRequestId(request.getId());
 		tempRequesDto.setFullName(request.getFullName());
 		tempRequesDto.setStatus(request.getRequestStatus().getStatusName());
 		tempRequesDto.setDepartment(request.getHeadDepartment().getName());
 		tempRequesDto.setSupervisor(request.getSupervisor().getName());
 		return tempRequesDto;
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