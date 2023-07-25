package com.ftg.accountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftg.accountservice.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	 Optional<User> getUserByEmail(String email);

}
