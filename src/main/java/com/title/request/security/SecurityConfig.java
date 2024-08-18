package com.title.request.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(configurer ->
		configurer
				.requestMatchers(HttpMethod.DELETE,"/api/requests/**").
				hasRole("ADMIN"));
		
		http.authorizeHttpRequests(configurer ->
		configurer
				.requestMatchers(HttpMethod.PUT,"/api/requests/**").
				hasRole("ADMIN"));
		

		http.authorizeHttpRequests(configurer ->
		configurer
				.requestMatchers(HttpMethod.GET,"/api/requests/**")
				.permitAll());
		
		http.authorizeHttpRequests(configurer ->
		configurer
				.requestMatchers(HttpMethod.POST,"/api/requests/**")
				.permitAll());
		
		
//		http.authorizeHttpRequests(configurer ->
//		configurer
//				.requestMatchers(HttpMethod.GET,"/api/requests/**").
//				hasRole("ADMIN"));
//		
		
		http.authorizeHttpRequests(configurer-> configurer.requestMatchers(
				HttpMethod.POST,"api/user/**").permitAll());
		
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
