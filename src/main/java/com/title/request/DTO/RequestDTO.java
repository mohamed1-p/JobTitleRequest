package com.title.request.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
	
	
	private String managerCode;
	private String supervisorCode;
	private String headDepartmentCode;
	private String unitHeadCode;
	private String fullName;
	

}
