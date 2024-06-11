package com.aditya.SpringSecurity.services.IMPL;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.aditya.SpringSecurity.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{

	
	public String generateToken(UserDetails userdetails) {
		
		return Jwts.builder().setSubject(userdetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000*60*24)).signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
		
	}
	
	private Key getSignKey() {
		byte[] key = Decoders.BASE64.decode("hGmMyRywJ6ILUIU+I+EgLk6+WQ99fiLzhPTxWAuqsqlhZXKtYNP6ss3utLrOhbAb");
		return Keys.hmacShaKeyFor(key);
		
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	private<T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	
	public boolean isTokenVaild(String token, UserDetails userdetails) {
		final String username = extractUsername(token);
		
		return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
	
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
	
	
public String generateRefreshToken(Map<String,Object> extraClaims , UserDetails userdetails) {
		
		return Jwts.builder().setClaims(extraClaims).setSubject(userdetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 604800000)).signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
		
	}
	

	
}
