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
@RequestMapping("api/restaurant-service")
@AllArgsConstructor
public class RestauranController {

	private final Logger logger = LoggerFactory.getLogger(RestauranController.class);

	private RestaurantServiceImpl restaurantService;

	@GetMapping("/test")
	public String cTest() {
		return "Restaurant controller works";
	}

	@PostMapping
	public ResponseEntity<RestaurantDTO> addRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
		try {
			RestaurantDTO savedObj = restaurantService.addRestaurant(restaurantDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedObj);
		} catch (Exception e) {
			logger.error("Failed to create restaurant: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create restaurant");
		}
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
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurant() {
		try {
			List<RestaurantDTO> allRestaurats = restaurantService.getAllRestaurats();
			return ResponseEntity.ok(allRestaurats);
		} catch (Exception e) {
			logger.error("Failed to retrieve all restaurants: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve all restaurants");
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long id,
			@RequestBody RestaurantDTO restaurantDTO) {
		try {
			RestaurantDTO savedObj = restaurantService.updateRestaurant(id, restaurantDTO);
			return ResponseEntity.status(HttpStatus.OK).body(savedObj);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", id, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to update restaurant with id {}: {}", id, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update restaurant");
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

	@PostMapping("/{restaurantId}/menu-items")
	public ResponseEntity<MenuItem> createMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItem menuItem) {
		try {
			MenuItem createMenuItem = restaurantService.createMenuItem(restaurantId, menuItem);
			return ResponseEntity.ok().body(createMenuItem);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to create menu item for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create menu item");
		}
	}

	@DeleteMapping("/{restaurantId}/menu-items/{menuItemId}")
	public ResponseEntity<?> deleteMenuItem(@PathVariable Long restaurantId, @PathVariable Long menuItemId) {
		try {
			restaurantService.deleteMenuItem(restaurantId, menuItemId);
			return ResponseEntity.ok("deleted successfully");
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to delete menu item with id {} for restaurant with id {}: {}", menuItemId,
					restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete menu item");
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
			throw  new ResourceNotFoundException("Restaurant", "ID", restaurantId);
		} catch (Exception e) {
			logger.error("Failed to retrieve address for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve address");
		}
	}

	@PutMapping("/{restaurantId}/address")
	public ResponseEntity<Address> updateAddress(@PathVariable Long restaurantId, @RequestBody Address address) {
		try {
			Address updateAddress = restaurantService.updateAddress(restaurantId, address);
			return ResponseEntity.ok(updateAddress);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to update address for restaurant with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update address");
		}
	}

	// ---------------------ContactDetails Specific EndPoints--------------------//

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

	@PutMapping("/{restaurantId}/contact-details")
	public ResponseEntity<ContactDetails> updateContactDetails(@PathVariable Long restaurantId,
			@RequestBody ContactDetails contactDetails) {
		try {
			ContactDetails updateContactDetails = restaurantService.updateContactDetails(restaurantId, contactDetails);
			return ResponseEntity.ok(updateContactDetails);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to update contact details for restaurant with id {}: {}", restaurantId,
					e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update contact details");
		}
	}

}
