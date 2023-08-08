package com.ftg.restaurantservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftg.restaurantservice.dto.StatusUpdateDTO;
import com.ftg.restaurantservice.service.OrderServiceClient;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/order/restaurant-service")
@AllArgsConstructor
public class OrderController {

	OrderServiceClient orderServiceClient;

	private final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@PostMapping("/prepare")
	public ResponseEntity<String> prepareOrder(@RequestBody String orderId) throws InterruptedException {

		logger.info("order taken for the order Id ::" + orderId);
		orderServiceClient.updateOrderStatus(new StatusUpdateDTO(orderId, "ORDER TAKEN BY RESTAURANT", "15min"));
		Thread.sleep(10000);
		logger.info("meal is now preparing for the order Id ::" + orderId);
		orderServiceClient.updateOrderStatus(new StatusUpdateDTO(orderId, "ORDER PREPARING BY RESTAURANT", "15min"));
		Thread.sleep(10000);
		logger.info("meal is ready for the order Id ::" + orderId);
		orderServiceClient.updateOrderStatus(new StatusUpdateDTO(orderId, "ORDER PREPARED BY RESTAURANT", "15min"));
		Thread.sleep(10000);
		logger.info("meal is prepared and waiting to delivery agent to pickup for the order Id ::" + orderId);
		orderServiceClient
				.updateOrderStatus(new StatusUpdateDTO(orderId, "WAITING FOR THE DELIVERY AGENT TO PICK", "15min"));
		Thread.sleep(10000);
		logger.info("Order is pickedup by delivery agent for the order Id ::" + orderId);
		orderServiceClient.updateOrderStatus(new StatusUpdateDTO(orderId, "ORDER PICKEDUP BY DELIVERY AGENT", "30min"));
		return ResponseEntity.ok(orderId);
	}
}
