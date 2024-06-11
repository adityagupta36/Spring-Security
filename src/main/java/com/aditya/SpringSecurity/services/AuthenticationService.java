package com.aditya.SpringSecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aditya.SpringSecurity.dto.JwtAuthenticationResponse;
import com.aditya.SpringSecurity.dto.RefreshTokenRequest;
import com.aditya.SpringSecurity.dto.SignInRequest;
import com.aditya.SpringSecurity.dto.SignUpRequest;
import com.aditya.SpringSecurity.entities.User;

@Component
public interface AuthenticationService {
	 
	
	public User signUp(SignUpRequest signuUpRequest);

	public JwtAuthenticationResponse signIn(SignInRequest signInRequest) ;

	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) ;

		
}
