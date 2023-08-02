package com.ftg.orderservice.rs.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

	private Long restaurantId;
	
	private String name;

	private List<MenuItem> menuItem;

	private Address address;

	private ContactDetails contactDetails;

	private String openingHour;
}
