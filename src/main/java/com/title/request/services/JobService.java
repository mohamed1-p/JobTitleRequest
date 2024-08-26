package com.title.request.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.title.request.DTO.JobDto;
import com.title.request.models.HeadDepartment;
import com.title.request.models.Manager;
import com.title.request.models.Supervisor;
import com.title.request.models.UnitHead;
import com.title.request.repository.HeadDepartmentRepository;
import com.title.request.repository.ManagerRepository;
import com.title.request.repository.SupervisorRepository;
import com.title.request.repository.UnitHeadRepository;

@Service
public class JobService {

    private ManagerRepository managerRepository;
    private SupervisorRepository supervisorRepository;
    private HeadDepartmentRepository headDepartmentRepository;
    private UnitHeadRepository unitHeadRepository;
    
    @Autowired
	public JobService(ManagerRepository managerRepository, SupervisorRepository supervisorRepository,
			HeadDepartmentRepository headDepartmentRepository, UnitHeadRepository unitHeadRepository) {
		
		this.managerRepository = managerRepository;
		this.supervisorRepository = supervisorRepository;
		this.headDepartmentRepository = headDepartmentRepository;
		this.unitHeadRepository = unitHeadRepository;
	}
    
    public List<JobDto> getAvailableJobsForPosition(String position) {
        switch (position.toLowerCase()) {
            case "manager":
                return managerRepository.findAll().stream()
                    .map(this::convertManagerToJobDTO)
                    .collect(Collectors.toList());
            case "supervisor":
                return supervisorRepository.findAll().stream()
                    .map(this::convertSupervisorToJobDTO)
                    .collect(Collectors.toList());
            case "headdepartment":
                return headDepartmentRepository.findAll().stream()
                    .map(this::convertHeadDepartmentToJobDTO)
                    .collect(Collectors.toList());
            case "unithead":
                return unitHeadRepository.findAll().stream()
                    .map(this::convertUnitHeadToJobDTO)
                    .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }
    
    
    private JobDto convertManagerToJobDTO(Manager manager) {
        return new JobDto(
            manager.getName(),
            "Manager",
            manager.getCode(),
            manager.getLocation(),
            manager.getSector()
        );
    }
    

    private JobDto convertSupervisorToJobDTO(Supervisor supervisor) {
        return new JobDto(
        		 supervisor.getName(),
                 "Supervisor",
                 supervisor.getCode(),
                 supervisor.getLocation(),
                 supervisor.getSector()
             );
         }

    private JobDto convertHeadDepartmentToJobDTO(HeadDepartment headDepartment) {
        return new JobDto(
        		headDepartment.getName(),
                 "Head Department",
                 headDepartment.getCode(),
                 headDepartment.getLocation(),
                 headDepartment.getSector()
             );
         }

    private JobDto convertUnitHeadToJobDTO(UnitHead unitHead) {
        return new JobDto(
        		unitHead.getName(),
                 "Unit Head",
                 unitHead.getCode(),
                 unitHead.getLocation(),
                 unitHead.getSector()
             );
         }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    



