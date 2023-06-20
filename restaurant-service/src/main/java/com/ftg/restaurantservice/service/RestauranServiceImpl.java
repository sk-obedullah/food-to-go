package com.ftg.restaurantservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ftg.restaurantservice.dto.RestaurantDTO;
import com.ftg.restaurantservice.model.MenuItem;
import com.ftg.restaurantservice.model.Restaurant;
import com.ftg.restaurantservice.repository.RestaurantRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestauranServiceImpl implements RestauranSerive {

	private RestaurantRepository restaurantRepository;

	@Override
	public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO) {
		Restaurant restaurant = new Restaurant();
		for (MenuItem item : restaurantDTO.getMenuItem()) {
			restaurant.addItem(item);
		}
		restaurant.setMenuItem(restaurantDTO.getMenuItem());
		restaurant.setAddress(restaurantDTO.getAddress());
		restaurant.setContactDetails(restaurantDTO.getContactDetails());
		Restaurant save = restaurantRepository.save(restaurant);
		RestaurantDTO dto = new RestaurantDTO(save.getName(), save.getMenuItem(), save.getAddress(),
				save.getContactDetails(), save.getOpeningHour());
		return dto;
	}

	@Override
	public RestaurantDTO getRestaurantById(Long restaurantId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RestaurantDTO> getAllRestaurats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRestaurant(Long restaurantID) {
		// TODO Auto-generated method stub

	}

}
