package com.title.request.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.title.request.DTO.ResponsePage;
import com.title.request.DTO.ShowRequestDto;
import com.title.request.models.HeadDepartment;
import com.title.request.models.Request;
import com.title.request.repository.HeadDepartmentRepository;

@Service
public class HeadDepartmentService {

    private final HeadDepartmentRepository headDepartmentRepository;

    @Autowired
    public HeadDepartmentService(HeadDepartmentRepository headDepartmentRepository) {
        this.headDepartmentRepository = headDepartmentRepository;
    }

    public ResponsePage<HeadDepartment> findAll(int pageNo, int pageSize) {

    	Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<HeadDepartment> departmentsPage = headDepartmentRepository.findAll(pageable);
        List<HeadDepartment> departments = departmentsPage.getContent();
        
    	return mapRequestToPageOject(departmentsPage,departments);
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
    
    
    
    
    
    
    
    
  
    
    private ResponsePage<HeadDepartment> mapRequestToPageOject(Page<HeadDepartment> departmentsPage,
    		List<HeadDepartment> department ){
    	
    	ResponsePage<HeadDepartment> content = new ResponsePage<>();
    	content.setContent(department);
      	content.setPage(departmentsPage.getNumber());
      	content.setSize(departmentsPage.getSize());
      	content.setTotalElements(departmentsPage.getTotalElements());
      	content.setTotalpages(departmentsPage.getTotalPages());
      	content.setLast(departmentsPage.isLast());
      	
    	return content;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}