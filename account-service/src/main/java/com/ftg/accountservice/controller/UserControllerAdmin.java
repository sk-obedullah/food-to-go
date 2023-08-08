package com.ftg.accountservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftg.accountservice.model.User;
import com.ftg.accountservice.model.UserDTO;
import com.ftg.accountservice.service.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/admin/user-service")
@AllArgsConstructor
public class UserControllerAdmin {

	private final Logger logger = LoggerFactory.getLogger(UserControllerAdmin.class);

	UserService userService;

	@GetMapping("/test")
	public String test() {
		return "user Admin service working";
	}

	@GetMapping()
	public List<UserDTO> getAllUsers() {
		List<UserDTO> userDTOs = new ArrayList<>();
		List<User> users = userService.getAll();
		for (User user : users) {
			UserDTO userDto = new UserDTO();
			userDto.setUserEmail(user.getEmail());
			userDto.setUserId(user.getId().intValue());
			userDto.setUserCity(user.getUserCity());
			userDto.setUserMobile(user.getUserMobile());
			userDto.setUserName(user.getName());
			userDTOs.add(userDto);
		}
		return userDTOs;
	}

	@GetMapping("/user/{email}")
	public User getUserByUserName(@PathVariable String email) {
		User userByuserName = userService.getUserByuserName(email);
		return userByuserName;
	}

}
