package com.title.request.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.title.request.DTO.AuthResponse;
import com.title.request.DTO.LoginDto;
import com.title.request.DTO.RegisterDto;

import com.title.request.security.JwtTokenProvider;
import com.title.request.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/user")
public class UserController {

	
	private UserService userService;
	
	@Autowired
	public UserController( UserService userService){
		this.userService=userService;
		
	}
	
	
	
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerNewUser(@RequestBody RegisterDto registerDto) {
		
		return ResponseEntity.ok(userService.register(registerDto));
	}
	
	
	@PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginDto LoginDto) {
      
		return ResponseEntity.ok(userService.authenticate(LoginDto));
    }

	
	
	
	@PutMapping("/{username}/make-admin")
	public ResponseEntity<String> makeUserAdmin(@PathVariable String username) {
        boolean success = userService.changeUserRoleToAdmin(username);
        if (success) {
        	
            return ResponseEntity.ok("User role updated to ADMIN.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user role.");
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
