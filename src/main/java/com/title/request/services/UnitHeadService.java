package com.title.request.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.title.request.DTO.ResponsePage;
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

    public ResponsePage<UnitHead> findAll(int pageNo, int pageSize) {
    	Pageable pageable = PageRequest.of(pageNo, pageSize);
		 Page<UnitHead> unitHeadPage =  unitHeadRepository.findAll(pageable);
		 List<UnitHead> unitHead = unitHeadPage.getContent();
		 
    	return mapunitHeadToPageObject(unitHeadPage,unitHead);
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
    
    
    
    private ResponsePage<UnitHead> mapunitHeadToPageObject(Page<UnitHead> headPage,
			 List<UnitHead> unitHead){
		
		ResponsePage<UnitHead> content = new ResponsePage<>();
		
		content.setContent(unitHead);
		content.setPage(headPage.getNumber());
	   	content.setSize(headPage.getSize());
	   	content.setTotalElements(headPage.getTotalElements());
	   	content.setTotalpages(headPage.getTotalPages());
	   	content.setLast(headPage.isLast());
	
		return content;
		
	}
}