package com.ftg.restaurantservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ftg.restaurantservice.dto.RestaurantDTO;
import com.ftg.restaurantservice.exception.ResourceNotFoundException;
import com.ftg.restaurantservice.model.Address;
import com.ftg.restaurantservice.model.ContactDetails;
import com.ftg.restaurantservice.model.MenuItem;
import com.ftg.restaurantservice.service.RestaurantServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/user/restaurant-service")
@AllArgsConstructor
public class RestauranControllerUser {

	private final Logger logger = LoggerFactory.getLogger(RestauranControllerUser.class);

	private RestaurantServiceImpl restaurantService;

	@GetMapping("/user-test")
	public String cTest() {
		return "Restaurant-user controller works";
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
		try {
			RestaurantDTO restaurantById = restaurantService.getRestaurantById(id);
			return ResponseEntity.ok(restaurantById);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", id, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to retrieve restaurant with id {}: {}", id, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve restaurant");
		}
	}

	@GetMapping
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurant(@RequestHeader("currentUser") String username,
			@RequestHeader("role") String role) {
		try {
			System.out.println("Logged In As--" + username + "-------------------------------");
			System.out.println("Logged In As--" + role + "-------------------------------");
			List<RestaurantDTO> allRestaurats = restaurantService.getAllRestaurats();
			return ResponseEntity.ok(allRestaurats);
		} catch (Exception e) {
			logger.error("Failed to retrieve all restaurants: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve all restaurants");
		}
	}

	// --------------------MenuItem Specific Controller EndPoints-----------------//

	@GetMapping("/{restaurantId}/menu-items")
	public ResponseEntity<List<MenuItem>> getMenuItems(@PathVariable Long restaurantId) {
		try {
			List<MenuItem> menuItems = restaurantService.getMenuItems(restaurantId);
			return ResponseEntity.ok(menuItems);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to retrieve menu items for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve menu items");
		}
	}

	@GetMapping("/{restaurantId}/menu-items/{itemId}")
	public ResponseEntity<MenuItem> getMenuItemByMenuId(@PathVariable Long restaurantId,@PathVariable Long itemId) {
		try {
			MenuItem menuItem = restaurantService.getMenuItemByMenuId(restaurantId,itemId);
			return ResponseEntity.ok(menuItem);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to retrieve menu items for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve menu items");
		}
	}
	
	// -------------------Address Specific Controller EndPoints-----------------//

	@GetMapping("/{restaurantId}/address")
	public ResponseEntity<Address> getAddress(@PathVariable Long restaurantId) {
		try {
			Address address = restaurantService.getAddress(restaurantId);
			return ResponseEntity.ok(address);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", restaurantId, e.getMessage());
			throw new ResourceNotFoundException("Restaurant", "ID", restaurantId);
		} catch (Exception e) {
			logger.error("Failed to retrieve address for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve address");
		}
	}

	@GetMapping("/{restaurantId}/contact-details")
	public ResponseEntity<ContactDetails> getContactDetails(@PathVariable Long restaurantId) {
		try {
			ContactDetails contactDetails = restaurantService.getContactDetails(restaurantId);
			return ResponseEntity.ok(contactDetails);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to retrieve contact details for restaurant with id {}: {}", restaurantId,
					e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve contact details");
		}
	}

}
