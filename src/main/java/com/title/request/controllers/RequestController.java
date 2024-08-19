package com.title.request.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.title.request.DTO.RequestDTO;
import com.title.request.DTO.ShowRequestDto;
import com.title.request.models.Request;
import com.title.request.models.UserEntity;
import com.title.request.services.RequestService;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

	private final RequestService requestService;
	
	@Autowired
	public RequestController(RequestService requestService) {
	
		this.requestService=requestService;
	}
	
	
	@GetMapping("/show")
	public ResponseEntity<List<ShowRequestDto>> showRequests(){
		return new ResponseEntity<>(requestService.getAllRequests()
				,HttpStatus.OK);
	}
	
	@PostMapping("/create")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO requestDto) {
		System.out.println("in request controller");
        RequestDTO createdRequest = requestService.createRequest(requestDto);
        System.out.println("after calling service");
        return ResponseEntity.ok(createdRequest);
    }
	
	//need adjustment for the params 
	@GetMapping("/creator")
    public ResponseEntity<List<Request>> getRequestsByCreator(@RequestParam UserEntity creator) {
        List<Request> requests = requestService.findByCreator(creator);
        return ResponseEntity.ok(requests);
    }
	
	//needs Dto to make the respnse more readable
	@GetMapping("/status")
    public ResponseEntity<List<Request>> getRequestsByStatus(@RequestParam int status) {
        List<Request> requests = requestService.findByStatus(status);
        return ResponseEntity.ok(requests);
    }
	
	@GetMapping("/date-range")
    public ResponseEntity<List<ShowRequestDto>> getRequestsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	    LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
	    LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
		
        List<ShowRequestDto> requests = requestService.findByDateRange(startDateTime, endDateTime);
        return ResponseEntity.ok(requests);
    }
	
	
	@PutMapping("/{requestId}/status")
    public ResponseEntity<Request> updateRequestStatus(
            @PathVariable Long requestId,
            @RequestParam int status,
            @RequestBody String comments) {
        Request updatedRequest = requestService.updateRequestStatus(requestId, status, comments);
        return ResponseEntity.ok(updatedRequest);
    }
}

























