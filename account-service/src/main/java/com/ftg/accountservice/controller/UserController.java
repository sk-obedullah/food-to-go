package com.ftg.accountservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftg.accountservice.model.User;
import com.ftg.accountservice.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/user-service")
@AllArgsConstructor
public class UserController {
	
	UserService service;

	@GetMapping("/test")
	public String test() {
		return "user service working";
	}
	
	@GetMapping("/addDefaultUser")
	public String addTestUser() {
		User user=new User(1l, "obedullah", "Obedullah@gmail.com", "Obed@8093", "ROLE_ADMIN", "Default User");
		User addUser = service.addUser(user);
		return "Default User Added Successfully";
	}
	
	@GetMapping("/user/{email}")
	public User addTestUser(@PathVariable String email) {
		User userByuserName = service.getUserByuserName(email);
		return userByuserName ;
	}
	
}
