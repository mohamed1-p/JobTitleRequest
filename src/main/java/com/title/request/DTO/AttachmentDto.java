package com.title.request.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {

	private Long attachmentId;
	private String fileName;
	private String attachmentType;
	private Long requestId; 
	
}
