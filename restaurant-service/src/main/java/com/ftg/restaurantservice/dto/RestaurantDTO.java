package com.ftg.restaurantservice.dto;

import java.util.List;

import com.ftg.restaurantservice.model.Address;
import com.ftg.restaurantservice.model.ContactDetails;
import com.ftg.restaurantservice.model.MenuItem;

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
	
	private String restaurantLocation;

	private ContactDetails contactDetails;
	
	private String restaurantImage;

	private String openingHour;
	
}
