//package com.ftg.accountservice.service;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.ftg.accountservice.dto.AuthRequest;
//import com.ftg.accountservice.model.Role;
//import com.ftg.accountservice.model.UserNew;
//import com.ftg.accountservice.repository.NewUserRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationServiceImpl implements AuthenticationService {
//	private final NewUserRepository userRepository;
//	private final PasswordEncoder passwordEncoder;
//	private final JwtServiceImpl jwtService;
//	private final AuthenticationManager authenticationManager;
//
//	@Override
//	public UserNew signup(UserNew request) {
//		request.setRole(Role.USER);
//		request.setPassword(passwordEncoder.encode(request.getPassword()));
//		UserNew save = userRepository.save(request);
//		return save;
//	}
//
//	@Override
//	public String signin(AuthRequest request) {
//		authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//		var user = userRepository.findByEmail(request.getUsername())
//				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
//		var jwt = jwtService.generateToken(user);
//		return jwt;
//	}
//}