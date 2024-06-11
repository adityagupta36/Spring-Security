package com.aditya.SpringSecurity.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aditya.SpringSecurity.services.JwtService;
import com.aditya.SpringSecurity.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private  JwtService jwtService;

	@Autowired
	private  UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	final String authHeader = request.getHeader("Authentication");
	final String jwt;
	final String userEmail;
	
		
		
		if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
		      filterChain.doFilter(request, response);
		      return;
		    }
		
		jwt=authHeader.substring(7);
		userEmail=jwtService.extractUsername(jwt);
		
		
		if(!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = userService.userDetailService().loadUserByUsername(userEmail);
			if(jwtService.isTokenVaild(jwt, userDetails)) {
				
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				securityContext.setAuthentication(token);
				
				SecurityContextHolder.setContext(securityContext);
				
			}
		}
	}
	
}

	

	
	
	

	

