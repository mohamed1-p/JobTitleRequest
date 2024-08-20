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
import com.title.request.DTO.ResponsePage;
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
	public ResponseEntity<ResponsePage<ShowRequestDto>> showRequests(
			@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
			@RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
		
		return new ResponseEntity<>(requestService.getAllRequests(pageNo,pageSize)
				,HttpStatus.OK);
	}
	
	@PostMapping("/create")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO requestDto) {
        RequestDTO createdRequest = requestService.createRequest(requestDto);
        return ResponseEntity.ok(createdRequest);
    }
	
	
	@GetMapping("/creator")
    public ResponseEntity<ResponsePage<ShowRequestDto>> getRequestsByCreator(@RequestParam Long creatorId,
    		@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
			@RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
		ResponsePage<ShowRequestDto> requests = requestService.findByCreator(creatorId,pageNo,pageSize);
        return ResponseEntity.ok(requests);
    }
	
	
	@GetMapping("/status")
    public ResponseEntity<ResponsePage<ShowRequestDto>> getRequestsByStatus(@RequestParam int status,
    		@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
			@RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
		ResponsePage<ShowRequestDto> requests = requestService.findByStatus(status,pageNo,pageSize);
        return ResponseEntity.ok(requests);
    }
	
	@GetMapping("/date-range")
    public ResponseEntity<ResponsePage<ShowRequestDto>> getRequestsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
			@RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	    LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
	    LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);
		
	    ResponsePage<ShowRequestDto> requests = requestService.findByDateRange(startDateTime, endDateTime,pageNo,pageSize);
        return ResponseEntity.ok(requests);
    }
	
	
	@PutMapping("/{requestId}/status")
    public ResponseEntity<ShowRequestDto> updateRequestStatus(
            @PathVariable Long requestId,
            @RequestParam int status,
            @RequestBody String comments) {
        ShowRequestDto updatedRequest = requestService.updateRequestStatus(requestId, status, comments);
        return ResponseEntity.ok(updatedRequest);
    }
}

























