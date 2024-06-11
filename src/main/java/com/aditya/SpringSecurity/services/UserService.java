package com.aditya.SpringSecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.aditya.SpringSecurity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
public interface UserService {
	

	public UserDetailsService userDetailService();

}