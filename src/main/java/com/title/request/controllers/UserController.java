package com.title.request.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.title.request.DTO.LoginDto;
import com.title.request.DTO.RegisterDto;

import com.title.request.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/user")
public class UserController {

	
	private AuthenticationManager authenticationManager;
	
	private UserService userService;
	
	@Autowired
	public UserController(AuthenticationManager authenticationManager,
						  UserService userService){
		this.authenticationManager=authenticationManager;
		this.userService=userService;
		
	}
	
	
	
	
	@PostMapping("/register")
	public ResponseEntity<String> registerNewUser(@RequestBody RegisterDto entity) {
		if(userService.saveUser(entity)) {
			return new ResponseEntity<>("register success!",HttpStatus.OK);
		}
		
		return new ResponseEntity<>("register failed! user already exist",HttpStatus.BAD_REQUEST);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
		Authentication auth =  authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUserName(),
						loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return new ResponseEntity<>("welcome",HttpStatus.OK);
	}
	
	
	
	
	@PutMapping("/{username}/make-admin")
	public ResponseEntity<String> makeUserAdmin(@PathVariable String username) {
        boolean success = userService.changeUserRoleToAdmin(username);
        System.out.println(success);
        if (success) {
            return ResponseEntity.ok("User role updated to ADMIN.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user role.");
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
