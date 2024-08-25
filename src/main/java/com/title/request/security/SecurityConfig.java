package com.title.request.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenFilter jwtTokenFilter;
	private final AuthenticationProvider authenticationProvider;
	
	 
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.csrf(csrf-> csrf.disable());
		
		http.authorizeHttpRequests(configurer -> {
			
	        // Restrict DELETE requests to /api/requests/** to ADMIN role
	        configurer
	            .requestMatchers(HttpMethod.DELETE, "/api/requests/**")
	            .hasAuthority("ADMIN");

	        // Restrict PUT requests to /api/requests/** to ADMIN role
	        configurer
	            .requestMatchers(HttpMethod.PUT, "/api/requests/**")
	            .hasAuthority("ADMIN");

	        
	        // Allow all POST requests to /api/user/**
	        configurer
	            .requestMatchers(HttpMethod.POST, "api/user/**")
	            .permitAll();
	        
	        configurer
	            .requestMatchers(HttpMethod.PUT, "api/user/**")
	            .hasAuthority("ADMIN");
	        
	        configurer
            .requestMatchers(HttpMethod.GET, "api/attachments/**")
            .hasAuthority("ADMIN");
	       
	        
	        //authenticate every request that begins with this string
	        configurer
	            .requestMatchers("/api/**")
	            .authenticated();
	        
	       	        
	        
	    });
		
		http.addFilterBefore(jwtTokenFilter,UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authenticationProvider(authenticationProvider);
		
		
		
		
		
		return http.build();
	}
	

	
	
	
	
}
