package com.ftg.restaurantservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ftg.restaurantservice.dto.MenuItemDTO;
import com.ftg.restaurantservice.dto.RestaurantDTO;
import com.ftg.restaurantservice.exception.ResourceNotFoundException;
import com.ftg.restaurantservice.model.Address;
import com.ftg.restaurantservice.model.ContactDetails;
import com.ftg.restaurantservice.model.MenuItem;
import com.ftg.restaurantservice.service.RestaurantServiceImpl;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/admin/restaurant-service")
@AllArgsConstructor
public class RestaurantControllerAdmin {

	private final Logger logger = LoggerFactory.getLogger(RestaurantControllerAdmin.class);

	private RestaurantServiceImpl restaurantService;

	@RequestMapping("/test")
	public String test() {
		return "admin Restaurant API working";
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

	@DeleteMapping("/{restaurantId}")
	public ResponseEntity<?> deleterestaurant(@PathVariable Long restaurantId ) {
		try {
			restaurantService.deleteRestaurant(restaurantId);
			return ResponseEntity.ok("deleted successfully");
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to delete Restaurant  with id "+restaurantId,
					restaurantId, e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete Restaurant");
		}
	}
	
	// --------------------MenuItem Specific Controller EndPoints-----------------//

	@PostMapping("/{restaurantId}/menu-items")
	public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItemDTO menuItem) {
		try {
			int restaurantId = menuItem.getRestaurantId();
			MenuItem createMenuItem = restaurantService.createMenuItem(new Long(menuItem.getRestaurantId()), menuItem);
			return ResponseEntity.ok().body(createMenuItem);
		} catch (ResourceNotFoundException e) {
			logger.error("Restaurant not found with id {}: {}", menuItem.getRestaurantId(), e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
		} catch (Exception e) {
			logger.error("Failed to create menu item for restaurant with id {}: {}", menuItem.getRestaurantId(), e.getMessage());
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
