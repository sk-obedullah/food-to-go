package com.ftg.accountservice.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "name is required")
	@Size(min = 3, max = 30, message = "min 3 and max 30 characters are allowed")
	@Pattern(regexp = "[a-zA-Z]+( +[a-zA-Z]+)*", message = "Invalid Name (special character and numbers are not allowed")
	private String name;

	@Email(message = "Please provide a valid email address and must contain @/.")
	@Pattern(regexp = ".+@.+\\..+", message = "Invalid Email")
	private String email;

	@Size(min = 4, max = 20, message = "password length must be 4 character long")
	@NotNull(message = "password is required")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,}$", message = "Minimum eight characters, at least one uppercase letter, one lowercase letter and one number:")
	private String password;

	private String role;

	private String about;

}
