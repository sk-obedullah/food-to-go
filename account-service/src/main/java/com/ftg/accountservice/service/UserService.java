package com.ftg.accountservice.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftg.accountservice.model.User;
import com.ftg.accountservice.model.UserDTO;
import com.ftg.accountservice.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

	UserRepository userRepository;
	
	PasswordEncoder passwordEncoder;

	public UserDTO addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User retriveUser = userRepository.save(user);
		UserDTO userDto = new UserDTO();
		userDto.setUserEmail(retriveUser.getEmail());
		userDto.setUserId(retriveUser.getId().intValue());
		userDto.setUserCity(retriveUser.getUserCity());
		userDto.setUserMobile(retriveUser.getUserMobile());
		userDto.setUserName(retriveUser.getName());
		return userDto;
	}

	public User getUserByuserName(String email) {
		User userByuserName = userRepository.getUserByEmail(email).get();
		return userByuserName;
	}

	public List<User> getAll() {

		List<User> all = userRepository.findAll();
		return all;
	}

	public User getById(Long id) {

		User user = userRepository.findById(id).get();
		return user;

	}

	public boolean deleteById(Long id) {

		try {
			userRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public User update(User e, Long id) {

		User user = userRepository.findById(id).get();
		user.setName(e.getName());
		user.setPassword(e.getPassword());
		//user.setAbout(e.getAbout());

		User save = userRepository.save(user);
		return save;
	}
}
