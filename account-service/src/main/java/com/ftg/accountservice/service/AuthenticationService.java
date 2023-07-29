package com.ftg.accountservice.service;

import com.ftg.accountservice.dto.AuthRequest;
import com.ftg.accountservice.model.UserNew;

public interface AuthenticationService {
	
	UserNew signup(UserNew request);
	String signin(AuthRequest request);
}
