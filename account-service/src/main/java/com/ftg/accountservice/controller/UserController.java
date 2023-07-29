package com.ftg.accountservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ftg.accountservice.dto.AuthRequest;
import com.ftg.accountservice.model.User;
import com.ftg.accountservice.service.AuthService;
import com.ftg.accountservice.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/user-service")
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

	@GetMapping("/addDefaultUser")
	public String addTestUser() {
		User user = new User(1l, "obedullah", "Obedullah@gmail.com", "Obed@8093", "ROLE_ADMIN", "Default User");
		User addUser = userService.addUser(user);
		return "Default User Added Successfully";
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		try {
			User addUser = userService.addUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(addUser);
		} catch (Exception e) {
			logger.error("Failed add User: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register user");
		}
	}
	

	@GetMapping("/user/{email}")
	public User getUserByUserName(@PathVariable String email) {
		User userByuserName = userService.getUserByuserName(email);
		return userByuserName;
	}
	
//	@GetMapping("/user")
//	public User getUsers() {
//		User userByuserName = userService.getUserByuserName1( );
//		return userByuserName;
//	}

	// ------------------------------------------------------------------------//

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
