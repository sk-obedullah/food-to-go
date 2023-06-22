package com.ftg.restaurantservice.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftg.restaurantservice.dto.RestaurantDTO;
import com.ftg.restaurantservice.model.MenuItem;
import com.ftg.restaurantservice.model.Restaurant;
import com.ftg.restaurantservice.repository.RestaurantRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestauranServiceImpl implements RestauranSerive {

	private RestaurantRepository restaurantRepository;
	
	private ModelMapper modelMapper;

	@Override
	public RestaurantDTO addRestaurant(RestaurantDTO restaurantDTO) {
//		Restaurant restaurant = new Restaurant();
//		List<MenuItem> menuItems=new ArrayList<>();
//		restaurant.setMenuItem(menuItems);
//		for (MenuItem item : restaurantDTO.getMenuItem()) {
//			restaurant.addItem(item);
//		}
//		//restaurant.setMenuItem(restaurantDTO.getMenuItem());
//		restaurant.setName(restaurantDTO.getName());
//		restaurant.setAddress(restaurantDTO.getAddress());
//		restaurant.setContactDetails(restaurantDTO.getContactDetails());
//		restaurant.setOpeningHour(restaurantDTO.getOpeningHour());
		Restaurant restaurant = modelMapper.map(restaurantDTO, Restaurant.class);
		Restaurant save = restaurantRepository.save(restaurant);
		RestaurantDTO dto = new RestaurantDTO(1L,save.getName(), save.getMenuItem(), save.getAddress(),
				save.getContactDetails(), save.getOpeningHour());
		return dto;
	}

	@Override
	public RestaurantDTO getRestaurantById(Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
		RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
		return restaurantDTO;
	}

	@Override
	public List<RestaurantDTO> getAllRestaurats() {
		List<Restaurant> findAll = restaurantRepository.findAll();
		List<RestaurantDTO> dtoList=new ArrayList<>();
		for (Restaurant restaurant : findAll) {
			RestaurantDTO map = modelMapper.map(restaurant, RestaurantDTO.class);
			dtoList.add(map);
		}
		return dtoList;
	}

	@Override
	public RestaurantDTO updateRestaurant(Long id,RestaurantDTO restaurantDTO) {
		Restaurant restaurant = restaurantRepository.findById(id).get();
		restaurant.setName(restaurantDTO.getName());
		//restaurant.updateAddress(restaurantDTO.getAddress());
		//restaurant.updateContactDetails(restaurantDTO.getContactDetails());
		//restaurant.setMenuItem(restaurantDTO.getMenuItem());
		restaurant.setOpeningHour(restaurantDTO.getOpeningHour());

		Restaurant save = restaurantRepository.save(restaurant);
		RestaurantDTO map2 = modelMapper.map(save, RestaurantDTO.class);
		return map2;
	}

	@Override
	public void deleteRestaurant(Long restaurantID) {
		restaurantRepository.deleteById(restaurantID);
	}

}
