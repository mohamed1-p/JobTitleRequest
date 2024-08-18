package com.title.request.DTO;

import lombok.Data;

@Data
public class ShowRequestDto {

	private Long requestId;
	private String fullName;
	private int status;
	private String department;
	private String supervisor;
}
