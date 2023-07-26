package com.ftg.accountservice.controller;

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

import com.ftg.accountservice.dto.AuthRequest;
import com.ftg.accountservice.model.User;
import com.ftg.accountservice.service.AuthService;
import com.ftg.accountservice.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/user-service")
@AllArgsConstructor
public class UserController {

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

	@GetMapping("/user/{email}")
	public User addTestUser(@PathVariable String email) {
		User userByuserName = userService.getUserByuserName(email);
		return userByuserName;
	}

	// ------------------------------------------------------------------------//

	@PostMapping("/auth/token")
	public String getToken(@RequestBody AuthRequest authRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authenticate.isAuthenticated()) {
			return authService.generateToken(authRequest.getUsername());
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
