package com.aditya.SpringSecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.aditya.SpringSecurity.entities.Role;

@Repository
public interface UserRepository extends JpaRepository<com.aditya.SpringSecurity.entities.User, Integer>{
	

	Optional<User> findByEmail(String email);
	
	com.aditya.SpringSecurity.entities.User findByRole(Role role);


}
