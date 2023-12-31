package com.ftg.restaurantservice.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

	private LocalDateTime timestamp;
	private String message;
	private String path;
	private String errorCode;
}
