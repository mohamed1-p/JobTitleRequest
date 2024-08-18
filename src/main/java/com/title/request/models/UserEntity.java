package com.title.request.models;

import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

   
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns= @JoinColumn
			  (name = "user_id",referencedColumnName = "id"),
			  inverseJoinColumns = @JoinColumn(name = "Role_id",
			  referencedColumnName = "Id"))
	private List<RoleEntity> roles = new ArrayList<>();


    
    
   
    @OneToMany(mappedBy = "creator")
    private List<Request> requests;


















}