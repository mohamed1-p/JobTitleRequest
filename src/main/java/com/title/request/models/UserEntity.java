package com.title.request.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRole()))
                    .collect(Collectors.toList());
    }






















}