package com.ftg.orderservice.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ftg.orderservice.rs.dto.MenuItem;
import com.ftg.orderservice.rs.dto.RestaurantDTO;

@FeignClient(name = "RESTAURANT-SERVICE", path = "api/")
public interface RestaurantClient {
	
	@GetMapping("user/restaurant-service/")
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurant(@RequestHeader("currentUser") String username,@RequestHeader("role") String role);

	@GetMapping("user/restaurant-service/{restaurantId}/menu-items/{itemId}")
	public ResponseEntity<MenuItem> getMenuItemByMenuId(@PathVariable Long restaurantId,@PathVariable Long itemId);
	
	@PostMapping("order/restaurant-service/prepare")
	public ResponseEntity<String> prepareOrder(String orderId) throws InterruptedException;
}
