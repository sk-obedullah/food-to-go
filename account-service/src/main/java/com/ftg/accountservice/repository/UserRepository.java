package com.ftg.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftg.accountservice.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
