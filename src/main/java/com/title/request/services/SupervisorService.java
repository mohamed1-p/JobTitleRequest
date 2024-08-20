package com.title.request.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.title.request.DTO.ResponsePage;
import com.title.request.models.Manager;
import com.title.request.models.Supervisor;
import com.title.request.repository.SupervisorRepository;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    @Autowired
    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    public ResponsePage<Supervisor> findAll(int pageNo, int pageSize) {
    	
    	 Pageable pageable = PageRequest.of(pageNo, pageSize);
		 Page<Supervisor> supervisorsPage =  supervisorRepository.findAll(pageable);
		 List<Supervisor> supervisors = supervisorsPage.getContent();
		 
        return mapSupervisorToPageObject(supervisorsPage,supervisors);
      
    }

    public Supervisor findById(Long id) {
        return supervisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supervisor not found with id " + id));
    }

    public Supervisor createSupervisor(Supervisor supervisor) {
        return supervisorRepository.save(supervisor);
    }

    public Supervisor updateSupervisor(Long id, Supervisor supervisorDetails) {
        Supervisor supervisor = findById(id);
        supervisor.setName(supervisorDetails.getName());
        supervisor.setCode(supervisorDetails.getCode());
        return supervisorRepository.save(supervisor);
    }

    public void deleteSupervisor(Long id) {
        Supervisor supervisor = findById(id);
        supervisorRepository.delete(supervisor);
    }
    
    
    
    
    private ResponsePage<Supervisor> mapSupervisorToPageObject(Page<Supervisor> supervisorsPage,
			 List<Supervisor> supervisor){
		
		ResponsePage<Supervisor> content = new ResponsePage<>();
		
		content.setContent(supervisor);
		content.setPage(supervisorsPage.getNumber());
    	content.setSize(supervisorsPage.getSize());
    	content.setTotalElements(supervisorsPage.getTotalElements());
    	content.setTotalpages(supervisorsPage.getTotalPages());
    	content.setLast(supervisorsPage.isLast());
	
		return content;
		
	}
}
