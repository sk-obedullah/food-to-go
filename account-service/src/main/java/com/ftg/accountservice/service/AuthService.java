package com.ftg.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private JwtService jwtService;

	public String generateToken(String username,String role) {
		return jwtService.generateToken(username,role);
	}

	public void validateToken(String token) {
		jwtService.validateToken(token);
	}

}
