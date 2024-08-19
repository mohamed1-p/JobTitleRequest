package com.title.request.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(configurer -> {
	        // Restrict DELETE requests to /api/requests/** to ADMIN role
	        configurer
	            .requestMatchers(HttpMethod.DELETE, "/api/requests/**")
	            .hasAuthority("ADMIN");

	        // Restrict PUT requests to /api/requests/** to ADMIN role
	        configurer
	            .requestMatchers(HttpMethod.PUT, "/api/requests/**")
	            .hasAuthority("ADMIN");

	        // Allow all GET requests to /api/requests/** without authentication
	        configurer
	            .requestMatchers(HttpMethod.GET, "/api/requests/**")
	            .permitAll();

	        // Require authentication for POST requests to /api/requests/**
	        configurer
	            .requestMatchers(HttpMethod.POST, "/api/requests/**")
	            .authenticated();

	        // Allow all POST requests to /api/user/**
	        configurer
	            .requestMatchers(HttpMethod.POST, "api/user/**")
	            .permitAll();

	        // Require authentication for POST requests to /api/attachments/**
	        configurer
	            .requestMatchers(HttpMethod.POST, "api/attachments/**")
	            .authenticated();

	        // Require authentication for GET requests to /api/attachments/**
	        configurer
	            .requestMatchers(HttpMethod.GET, "api/attachments/**")
	            .authenticated();
	        
	        configurer
            .requestMatchers(HttpMethod.PUT, "api/user/**")
            .permitAll();
	    });
		
		http.httpBasic(Customizer.withDefaults());

		
		
		
		
		http.csrf(csrf-> csrf.disable());
		return http.build();
	}
	

	@Bean
	AuthenticationManager authenticationManger(
			AuthenticationConfiguration authenticationConfiguration) 
			throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	}
	
}
