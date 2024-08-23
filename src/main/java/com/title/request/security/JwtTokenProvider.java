package com.title.request.security;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenProvider {

	private static final String SECRET_KEY = "67cf53ce5e0b0fac530a3ccf98e2e530ffa335737a5082f487f25c091bab7ad7";
	
	
	
	public String generateToken(Map<String, Object> extraClaims,UserDetails userDetials) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetials.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000*60*24))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	
	public String generateToken(UserDetails userDetials) {
		return generateToken(new HashMap<>(),userDetials);
	}
	
	
	
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims , T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build().parseClaimsJws(token)
				.getBody();
	}
	
	//understand what hmacshakeyfor does
	private  Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	
	}


	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}


	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
