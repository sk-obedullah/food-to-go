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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftg.orderservice.models.Order;
import com.ftg.orderservice.service.OrderServiceImpl;
import com.ftg.orderservice.service.PaymentService;
import com.ftg.orderservice.service.RestaurantClient;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/admin/order-service")
@AllArgsConstructor
public class AdminOrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	private OrderServiceImpl orderService;
	private PaymentService paymentService;
	private RestaurantClient restaurantClient;

	@GetMapping("/test")
	public String cTest() {
		return "admin order controller works";
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
		logger.info("Entering deleteOrder method with orderId: " + orderId);
		try {
			orderService.deleteOrder(orderId);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			logger.error("An error occurred while deleting the order." + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		logger.info("Entering getAllOrders method.");
		try {
			List<Order> orders = orderService.getAllOrders();
			return ResponseEntity.ok(orders);
		} catch (Exception e) {
			logger.error("An error occurred while fetching all orders." + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
