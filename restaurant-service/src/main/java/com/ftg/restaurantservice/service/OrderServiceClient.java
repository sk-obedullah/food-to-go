package com.ftg.restaurantservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ftg.restaurantservice.dto.StatusUpdateDTO;

@FeignClient(name = "ORDER-SERVICE", path = "api/")
public interface OrderServiceClient {

	@PostMapping("order/order-service/update")
	public ResponseEntity<?> updateOrderStatus(@RequestBody StatusUpdateDTO updates);
}
