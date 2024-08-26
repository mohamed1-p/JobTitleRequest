package com.title.request.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ResponsePage<T> {

	private List<T> content;
	private int page;
	private int size;
	private long totalElements;
	private int totalpages;
	private boolean Last;
	
}
