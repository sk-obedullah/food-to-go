package com.ftg.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

	private Long id;

	private String address;

	private String zip;

	private String nearBy;
}
