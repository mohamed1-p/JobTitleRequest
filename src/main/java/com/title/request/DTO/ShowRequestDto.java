package com.title.request.DTO;

import lombok.Data;

@Data
public class ShowRequestDto {

	private Long requestId;
	private String fullName;
	private String creatorName;
	private String positionType;
	private String positionCode;
	private String status;

}
