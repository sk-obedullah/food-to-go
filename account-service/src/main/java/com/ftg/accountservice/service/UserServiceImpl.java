package com.ftg.accountservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ftg.accountservice.repository.NewUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserServiceImpl implements UserServiceNew {

	private final NewUserRepository userRepository;

	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) {
				return userRepository.findByEmail(username)
						.orElseThrow(() -> new UsernameNotFoundException("user not found ::" + username));
			}
		};
	}
}
