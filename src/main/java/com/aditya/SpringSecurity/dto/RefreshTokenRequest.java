package com.aditya.SpringSecurity.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RefreshTokenRequest {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
