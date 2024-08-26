package com.title.request.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.title.request.DTO.JobDto;
import com.title.request.DTO.ResponsePage;
import com.title.request.models.HeadDepartment;
import com.title.request.models.Manager;
import com.title.request.models.Request;
import com.title.request.models.Supervisor;
import com.title.request.models.UnitHead;
import com.title.request.repository.HeadDepartmentRepository;
import com.title.request.repository.ManagerRepository;
import com.title.request.repository.SupervisorRepository;
import com.title.request.repository.UnitHeadRepository;

@Service
public class JobService {

    private ManagerService managerService;
    private SupervisorService supervisorService;
    private HeadDepartmentService headDepartmentService;
    private UnitHeadService unitHeadService;
    
    @Autowired
	public JobService(ManagerService managerService,UnitHeadService unitHeadService,
			SupervisorService supervisorService,HeadDepartmentService headDepartmentService) {
		
		this.managerService=managerService;
		this.supervisorService = supervisorService;
		this.headDepartmentService=headDepartmentService;
		this.unitHeadService=unitHeadService;
	}
    
    public ResponsePage<?> getAvailableJobsForPosition(String position, int pageNo, int pageSize) {
    	
    	
    	   switch (position.toLowerCase()) {
           case "manager":
        	   return managerService.findAll(pageNo, pageSize);
        	   
           case "supervisor":
             return supervisorService.findAll(pageNo, pageSize);
           case "headdepartment":
               return headDepartmentService.findAll(pageNo, pageSize);
           case "unithead":
               return unitHeadService.findAll(pageNo, pageSize);
           default:
               throw new IllegalArgumentException("Invalid position: " + position);
       }
        
    }
    
   
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    



