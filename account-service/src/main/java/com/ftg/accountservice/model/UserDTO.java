package com.ftg.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	private int userId;
	private String userName;
	private String userEmail;
	private String userMobile;
	private String userPassword;
	private String userCity;
}
