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

    public ResponsePage<JobDto> findAll(int pageNo, int pageSize) {
    	
    	 Pageable pageable = PageRequest.of(pageNo, pageSize);
		 Page<Supervisor> supervisorsPage =  supervisorRepository.findAll(pageable);
		 List<Supervisor> supervisors = supervisorsPage.getContent();
		 List<JobDto> jobDTo = supervisors.stream()
				 	.map(this::mapSupervisorToJobDTO).
				 	collect(Collectors.toList());
		 
     
		 
        return mapSupervisorToPageObject(supervisorsPage,jobDTo);
      
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
    
    
    
    
    private ResponsePage<JobDto> mapSupervisorToPageObject(Page<Supervisor> supervisorsPage,
			 List<JobDto> supervisor){
		
		ResponsePage<JobDto> content = new ResponsePage<>();
		
		content.setContent(supervisor);
		content.setPage(supervisorsPage.getNumber());
    	content.setSize(supervisorsPage.getSize());
    	content.setTotalElements(supervisorsPage.getTotalElements());
    	content.setTotalpages(supervisorsPage.getTotalPages());
    	content.setLast(supervisorsPage.isLast());
	
		return content;
		
	}
    
    
    private JobDto mapSupervisorToJobDTO(Supervisor supervisor) {
        JobDto dto = new JobDto();
        dto.setCode(supervisor.getCode());
        dto.setLocation(supervisor.getLocation());
        dto.setName(supervisor.getName());
        dto.setSector(supervisor.getSector());
        dto.setTitle("supervisor");
        return dto;
   }
}
