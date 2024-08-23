package com.title.request.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.title.request.DTO.AuthResponse;
import com.title.request.DTO.LoginDto;
import com.title.request.DTO.RegisterDto;
import com.title.request.models.RoleEntity;
import com.title.request.models.UserEntity;
import com.title.request.repository.RoleRepository;
import com.title.request.repository.UserRepository;
import com.title.request.security.JwtTokenProvider;

@Service
public class UserService  {
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
   
    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,
    		RoleRepository roleRepository,JwtTokenProvider jwtTokenProvider,
    		AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.roleRepository=roleRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.authenticationManager=authenticationManager;
    }

   
    public AuthResponse saveUser(RegisterDto userDto) {
    	if(userRepository.existsByusername(userDto.getUserName())) {
    	return null;
    	}
    	UserEntity user = new UserEntity();
    	user.setName(userDto.getName());
    	user.setUsername(userDto.getUserName());
    	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	
    	RoleEntity role = roleRepository.findByRole("EMPLOYEE").orElseThrow();
    	
    	user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        var jwtToken = jwtTokenProvider.generateToken(user);
        
        return AuthResponse.builder()
        		.accessToken(jwtToken)
        		.build();
    }
    
    
    public AuthResponse register(RegisterDto userDto) {
    	
    	RoleEntity role = roleRepository.findByRole("EMPLOYEE").orElseThrow();
    	userRepository.existsByusername(userDto.getUserName());
    	
    	var user = UserEntity.builder()
    			.username(userDto.getUserName())
    			.name(userDto.getName())
    			.password(passwordEncoder.encode(userDto.getPassword()))
    			.roles(Collections.singletonList(role))
    			.build();
    			
    	 userRepository.save(user);
         var jwtToken = jwtTokenProvider.generateToken(user);
         
         return AuthResponse.builder()
         		.accessToken(jwtToken)
         		.build();
    }
    

    public AuthResponse authenticate(LoginDto userDto) {
    	authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(
    					userDto.getUserName(), userDto.getPassword()));
    	
    	var user = userRepository.findByUsername(userDto.getUserName()).orElseThrow();
    	
    	var jwtToken = jwtTokenProvider.generateToken(user);
        
        return AuthResponse.builder()
        		.accessToken(jwtToken)
        		.build();
    }
    
    
    public Boolean changeUserRoleToAdmin(String username) {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("the user "+user);

        RoleEntity adminRole = roleRepository.findByRole("ADMIN")
            .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        
        //user.setRoles(Collections.singletonList(adminRole));
        user.getRoles().add(adminRole);
        //System.out.println(user.getRoles());
        userRepository.save(user);
        return true;
    }
    
    
    
}













