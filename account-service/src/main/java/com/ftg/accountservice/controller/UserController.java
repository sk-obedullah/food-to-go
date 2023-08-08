package com.ftg.accountservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ftg.accountservice.dto.AuthRequest;
import com.ftg.accountservice.model.User;
import com.ftg.accountservice.model.UserDTO;
import com.ftg.accountservice.service.AuthService;
import com.ftg.accountservice.service.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/user/user-service")
@AllArgsConstructor
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	UserService userService;
	private AuthService authService;
	private AuthenticationManager authenticationManager;

	@GetMapping("/test")
	public String test() {
		return "user service working";
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
		try {
			User user = new User();
			user.setName(userDTO.getUserName());
			user.setEmail(userDTO.getUserEmail());
			user.setUserCity(userDTO.getUserCity());
			user.setUserMobile(userDTO.getUserMobile());
			user.setPassword(userDTO.getUserPassword());
			user.setRole("ROLE_USER");
			UserDTO addUser = userService.addUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(addUser);
		} catch (Exception e) {
			logger.error("Failed add User: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register user");
		}
	}

	@PostMapping("/auth/token")
	public String getToken(@RequestBody AuthRequest authRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authenticate.isAuthenticated()) {
			User userByuserName = userService.getUserByuserName(authRequest.getUsername());
			return authService.generateToken(authRequest.getUsername(), userByuserName.getRole());
		} else {
			throw new RuntimeException("invalid access");
		}
	}

	@GetMapping("/auth/validate")
	public String validateToken(@RequestParam("token") String token) {
		authService.validateToken(token);
		return "Token is valid";
	}
}
