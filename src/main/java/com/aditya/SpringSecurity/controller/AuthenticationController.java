package com.aditya.SpringSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.SpringSecurity.dto.JwtAuthenticationResponse;
import com.aditya.SpringSecurity.dto.RefreshTokenRequest;
import com.aditya.SpringSecurity.dto.SignInRequest;
import com.aditya.SpringSecurity.dto.SignUpRequest;
import com.aditya.SpringSecurity.entities.User;
import com.aditya.SpringSecurity.services.AuthenticationService;

import jakarta.persistence.NamedStoredProcedureQuery;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private SignInRequest signInRequest;
	
	@Autowired
	private SignUpRequest signUpRequest;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody SignUpRequest signuUpRequest){
		return ResponseEntity.ok(authenticationService.signUp(signuUpRequest));
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
		return ResponseEntity.ok(authenticationService.signIn(signInRequest));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
	}

}
