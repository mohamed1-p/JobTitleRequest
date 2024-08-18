package com.title.request.models;

import jakarta.persistence.Column;
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
@Table(name ="roles")
public class RoleEntity {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int Id;
	private String role;
}
