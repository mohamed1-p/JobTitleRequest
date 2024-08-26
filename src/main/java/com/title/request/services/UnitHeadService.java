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
import com.title.request.models.UnitHead;
import com.title.request.repository.UnitHeadRepository;

@Service
public class UnitHeadService {

    private final UnitHeadRepository unitHeadRepository;

    @Autowired
    public UnitHeadService(UnitHeadRepository unitHeadRepository) {
        this.unitHeadRepository = unitHeadRepository;
    }

    public ResponsePage<JobDto> findAll(int pageNo, int pageSize) {
    	Pageable pageable = PageRequest.of(pageNo, pageSize);
		 Page<UnitHead> unitHeadPage =  unitHeadRepository.findAll(pageable);
		 List<UnitHead> unitHead = unitHeadPage.getContent();
		 
		 List<JobDto> jobDTo = unitHead.stream()
				 	.map(this::mapUnitHeadToJobDTO).
				 	collect(Collectors.toList());
		 
    	return mapunitHeadToPageObject(unitHeadPage,jobDTo);
    }

    public UnitHead findById(Long id) {
        return unitHeadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UnitHead not found with id " + id));
    }

    public UnitHead createUnitHead(UnitHead unitHead) {
        return unitHeadRepository.save(unitHead);
    }

    public UnitHead updateUnitHead(Long id, UnitHead unitHeadDetails) {
        UnitHead unitHead = findById(id);
        unitHead.setName(unitHeadDetails.getName());
        unitHead.setCode(unitHeadDetails.getCode());
        return unitHeadRepository.save(unitHead);
    }

    public void deleteUnitHead(Long id) {
        UnitHead unitHead = findById(id);
        unitHeadRepository.delete(unitHead);
    }
    
    
    
    private ResponsePage<JobDto> mapunitHeadToPageObject(Page<UnitHead> headPage,
			 List<JobDto> unitHead){
		
		ResponsePage<JobDto> content = new ResponsePage<>();
		
		content.setContent(unitHead);
		content.setPage(headPage.getNumber());
	   	content.setSize(headPage.getSize());
	   	content.setTotalElements(headPage.getTotalElements());
	   	content.setTotalpages(headPage.getTotalPages());
	   	content.setLast(headPage.isLast());
	
		return content;
		
	}
    
    
    
    private JobDto mapUnitHeadToJobDTO(UnitHead unitHead) {
        JobDto dto = new JobDto();
        dto.setCode(unitHead.getCode());
        dto.setLocation(unitHead.getLocation());
        dto.setName(unitHead.getName());
        dto.setSector(unitHead.getSector());
        dto.setTitle("unit Head");
        return dto;
   }
    
    
    
    
    
    
    
    
    
}