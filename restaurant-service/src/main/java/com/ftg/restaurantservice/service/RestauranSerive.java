package com.ftg.restaurantservice.service;

import java.util.List;

import com.ftg.restaurantservice.dto.RestaurantDTO;

public interface RestauranSerive {

	RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO);
	
	RestaurantDTO getRestaurantById(Long restaurantId);
	
	List<RestaurantDTO> getAllRestaurats();
	
	RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO);
	
	void deleteRestaurant(Long restaurantID);
	
	
}
