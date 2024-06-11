package com.aditya.SpringSecurity.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public interface JwtService {
	
	public String extractUsername(String token);
	
	String generateToken(UserDetails userdetails) ;
	
	public boolean isTokenVaild(String token, UserDetails userdetails) ;

	public String generateRefreshToken(Map<String,Object> extraClaims , UserDetails userdetails) ;

}
