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

import com.title.request.repository.HeadDepartmentRepository;

@Service
public class HeadDepartmentService {

    private final HeadDepartmentRepository headDepartmentRepository;

    @Autowired
    public HeadDepartmentService(HeadDepartmentRepository headDepartmentRepository) {
        this.headDepartmentRepository = headDepartmentRepository;
    }

    public ResponsePage<JobDto> findAll(int pageNo, int pageSize) {

    	Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<HeadDepartment> departmentsPage = headDepartmentRepository.findAll(pageable);
        List<HeadDepartment> departments = departmentsPage.getContent();
        
        List<JobDto> jobDTo = departments.stream()
			 	.map(this::mapDepartmentToJobDTO).
			 	collect(Collectors.toList());
    	return mapRequestToPageOject(departmentsPage,jobDTo);
    }

    public HeadDepartment findById(Long id) {
        return headDepartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Department Exist by this id"));
    }

    public HeadDepartment createHeadDepartment(HeadDepartment headDepartment) {
        return headDepartmentRepository.save(headDepartment);
    }

    public HeadDepartment updateHeadDepartment(Long id, HeadDepartment headDepartmentDetails) {
        HeadDepartment headDepartment = findById(id);
        headDepartment.setName(headDepartmentDetails.getName());
        headDepartment.setCode(headDepartmentDetails.getCode());
        return headDepartmentRepository.save(headDepartment);
    }

    public void deleteHeadDepartment(Long id) {
        HeadDepartment headDepartment = findById(id);
        headDepartmentRepository.delete(headDepartment);
    }
    
    
    
    
    
    
    
    
  
    
    private ResponsePage<JobDto> mapRequestToPageOject(Page<HeadDepartment> departmentsPage,
    		List<JobDto> department ){
    	
    	ResponsePage<JobDto> content = new ResponsePage<>();
    	content.setContent(department);
      	content.setPage(departmentsPage.getNumber());
      	content.setSize(departmentsPage.getSize());
      	content.setTotalElements(departmentsPage.getTotalElements());
      	content.setTotalpages(departmentsPage.getTotalPages());
      	content.setLast(departmentsPage.isLast());
      	
    	return content;
    }
    
    
    private JobDto mapDepartmentToJobDTO(HeadDepartment headDep) {
        JobDto dto = new JobDto();
        dto.setCode(headDep.getCode());
        dto.setLocation(headDep.getLocation());
        dto.setName(headDep.getName());
        dto.setSector(headDep.getSector());
        dto.setTitle("Head Department");
        return dto;
   }
    
    
    
    
    
    
    
    
    
    
}