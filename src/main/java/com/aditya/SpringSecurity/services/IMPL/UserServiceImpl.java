package com.aditya.SpringSecurity.services.IMPL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aditya.SpringSecurity.repository.UserRepository;
import com.aditya.SpringSecurity.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{
	
    @Autowired
	private UserRepository repo;
	
    @Autowired
	public UserDetailsService userDetailService() {

		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
				return repo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
			}
		};
	}

	
}
