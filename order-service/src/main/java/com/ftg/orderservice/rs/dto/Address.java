package com.ftg.orderservice.rs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {

	private Long id;

	private String address;

	private String zip;

	private String nearBy;

}
