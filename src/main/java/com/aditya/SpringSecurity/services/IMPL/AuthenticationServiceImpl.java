package com.aditya.SpringSecurity.services.IMPL;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aditya.SpringSecurity.dto.JwtAuthenticationResponse;
import com.aditya.SpringSecurity.dto.RefreshTokenRequest;
import com.aditya.SpringSecurity.dto.SignUpRequest;
import com.aditya.SpringSecurity.dto.SignInRequest;
import com.aditya.SpringSecurity.entities.Role;
import com.aditya.SpringSecurity.entities.User;
import com.aditya.SpringSecurity.repository.UserRepository;
import com.aditya.SpringSecurity.services.AuthenticationService;
import com.aditya.SpringSecurity.services.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private  PasswordEncoder passwordEncoder;
	
	@Autowired
	private SignUpRequest signuUpRequest;
	
	@Autowired
	private SignInRequest signInRequest;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	public User signUp(SignUpRequest signuUpRequest) {
		
		User user = new User();
		user.setEmail(signuUpRequest.getEmail());
		user.setFirstname(signuUpRequest.getFirstName());
		user.setLastname(signuUpRequest.getLastname());
		user.setRole(Role.User);
		user.setPassword(passwordEncoder.encode(signuUpRequest.getPassword()));
		
		return repo.save(user);
		
	}
	
	
	public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())) ;
		
		var user = repo.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Email or password"));
		
		var jwt = jwtService.generateToken(user);
		
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		
		return jwtAuthenticationResponse;
		
	}
	
	
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		
		String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
		
		org.springframework.security.core.userdetails.User user = repo.findByEmail(userEmail).orElseThrow();
		
		if(jwtService.isTokenVaild(refreshTokenRequest.getToken(), user)) {
			var jwt = jwtService.generateToken(user);
			
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			
			return jwtAuthenticationResponse;
		}
		
		return null;
		
	}
}
