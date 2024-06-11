package com.aditya.SpringSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.aditya.SpringSecurity.entities.Role;
import com.aditya.SpringSecurity.entities.User;
import com.aditya.SpringSecurity.repository.UserRepository;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {
	
	@Autowired
	private UserRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		User adminAccount = repo.findByRole(Role.Admin);
		if(null==adminAccount) {
			User user = new User();
			
			user.setEmail("admin@gmail.com");
			user.setFirstname("admin");
			user.setLastname("admin");
			user.setRole(Role.Admin);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			
			repo.save(user);
			
		}
	}

}
