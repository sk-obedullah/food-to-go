package com.ftg.accountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftg.accountservice.model.UserNew;

public interface NewUserRepository extends JpaRepository<UserNew, Integer> {

	Optional<UserNew> findByEmail(String email);
}
