package com.ftg.restaurantservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftg.restaurantservice.dto.RestaurantDTO;
import com.ftg.restaurantservice.model.Address;
import com.ftg.restaurantservice.model.ContactDetails;
import com.ftg.restaurantservice.model.MenuItem;
import com.ftg.restaurantservice.service.RestauranSerive;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/restaurant-service")
@AllArgsConstructor
public class RestauranController {

	private RestauranSerive restauranSerive;

	@RequestMapping("/test")
	public ResponseEntity<?> test() {
		return ResponseEntity.ok(new RestaurantDTO(1L,"testName", List.of(new MenuItem(), new MenuItem()), new Address(),
				new ContactDetails(), "testOpeningHour"));
	}

	@PostMapping
	public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
		RestaurantDTO savedObj = restauranSerive.addRestaurant(restaurantDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedObj);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
		RestaurantDTO restaurantById = restauranSerive.getRestaurantById(id);
		return ResponseEntity.ok(restaurantById);
	}

	@GetMapping
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurant() {
		List<RestaurantDTO> allRestaurats = restauranSerive.getAllRestaurats();
		return ResponseEntity.ok(allRestaurats);
	}

	
	@PutMapping("{id}")
	public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDTO restaurantDTO) {
		RestaurantDTO savedObj = restauranSerive.updateRestaurant(id,restaurantDTO);
		return ResponseEntity.status(HttpStatus.OK).body(savedObj);
	}
	
	
	//----------------------MenuItem Specific Controller methods-----------------//
	
	
	
	
}
