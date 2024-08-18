package com.title.request.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="request_status")
public class RequestStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int statusId;
	private String StatusName;

}
