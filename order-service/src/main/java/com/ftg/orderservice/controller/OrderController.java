package com.ftg.orderservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftg.orderservice.dto.OrderDTO;
import com.ftg.orderservice.dto.OrderMenuDTO;
import com.ftg.orderservice.models.Order;
import com.ftg.orderservice.rs.dto.RestaurantDTO;
import com.ftg.orderservice.service.OrderServiceImpl;
import com.ftg.orderservice.service.PaymentService;
import com.ftg.orderservice.service.RestaurantClient;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/user/order-service")
@AllArgsConstructor
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	private OrderServiceImpl orderService;
	private PaymentService paymentService;
	private RestaurantClient restaurantClient;

	@GetMapping("/test")
	public String cTest() {
		return "order controller works";
	}

	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody OrderMenuDTO orderDTO
			 ) {
		logger.info("Entering createOrder method.");
		try {
			OrderDTO dto=new OrderDTO();
			dto.setItems(orderDTO.getMenuItemIds());
			dto.setOrderId(orderDTO.getOrderId());
			dto.setRestaurantId(Long.parseLong(orderDTO.getRestaurantId()));
			dto.setUserId(orderDTO.getUserId());
			
			Order createdOrder = orderService.createOrder(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
		} catch (Exception e) {
			logger.error("An error occurred while creating the order." + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/pay/{orderId}")
	public String test(@PathVariable String orderId) {
		logger.info("Entering payment method with orderId: " + orderId);
		try {
			String initiatePayment = paymentService.initiatePayment(orderId);
			return initiatePayment;
		} catch (Exception e) {
			logger.error("An error occurred while processing the payment." + e.getMessage());
			return "Error occurred while processing the payment.";
		}
	}

	
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderByOrderId(@PathVariable String orderId) {
		logger.info("Entering getOrder method with orderId: " + orderId);
		try {
			Order order = orderService.getOrder(orderId);
			return ResponseEntity.ok(order);
		} catch (Exception e) {
			logger.error("An error occurred while fetching the order." + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getUserOrders(@RequestHeader("currentUser") String username) {
		logger.info("Entering getUserOrders method  to get All Orders for: " + username);
		try {
			List<Order> orders= orderService.getUserOrders(username );
			return ResponseEntity.ok(orders);
		} catch (Exception e) {
			logger.error("An error occurred while getting the orders for the user "+username +"::"+ e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/{orderId}")
	public ResponseEntity<Order> updateOrder(@PathVariable String orderId, @RequestBody OrderDTO orderDTO) {
		logger.info("Entering updateOrder method with orderId: " + orderId);
		try {
			Order updatedOrder = orderService.updateOrder(orderId, orderDTO);
			return ResponseEntity.ok(updatedOrder);
		} catch (Exception e) {
			logger.error("An error occurred while updating the order." + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	
}
