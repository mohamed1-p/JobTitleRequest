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
import com.title.request.repository.ManagerRepository;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public ResponsePage<JobDto> findAll(int pageNo, int pageSize) {
    	
    	 Pageable pageable = PageRequest.of(pageNo, pageSize);
		 Page<Manager> managersPage =  managerRepository.findAll(pageable);
		 List<Manager> managers = managersPage.getContent();
		 List<JobDto> jobDTo = managers.stream()
				 	.map(this::mapManagerToJobDTO).
				 	collect(Collectors.toList());
		 
        return mapManagerToPageObject(managersPage,jobDTo);
    }

    public Manager findById(Long id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found with id " + id));
    }

    public Manager createManager(Manager manager) {
        return managerRepository.save(manager);
    }

    public Manager updateManager(Long id, Manager managerDetails) {
        Manager manager = findById(id);
        manager.setName(managerDetails.getName());
        manager.setCode(managerDetails.getCode());
        return managerRepository.save(manager);
    }

    public void deleteManager(Long id) {
        Manager manager = findById(id);
        managerRepository.delete(manager);
    }
    
    
    
    
    
    
    
    
    private ResponsePage<JobDto> mapManagerToPageObject(Page<Manager> maangersPage,
			 List<JobDto> manager){
		
		ResponsePage<JobDto> content = new ResponsePage<>();
		
		content.setContent(manager);
		content.setPage(maangersPage.getNumber());
     	content.setSize(maangersPage.getSize());
     	content.setTotalElements(maangersPage.getTotalElements());
     	content.setTotalpages(maangersPage.getTotalPages());
     	content.setLast(maangersPage.isLast());
	
		return content;
		
	}
    
    private JobDto mapManagerToJobDTO(Manager manager) {
         JobDto dto = new JobDto();
         dto.setCode(manager.getCode());
         dto.setLocation(manager.getLocation());
         dto.setName(manager.getName());
         dto.setSector(manager.getSector());
         dto.setTitle("manager");
         return dto;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}