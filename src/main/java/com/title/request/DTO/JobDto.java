package com.title.request.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

	
	private String name;
    private String title;  
    private String code;
    private String location;
    private String sector;
}
