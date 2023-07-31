package com.ftg.orderservice.controller;

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

import com.ftg.orderservice.dto.OrderDTO;
import com.ftg.orderservice.models.Order;
import com.ftg.orderservice.service.OrderServiceImpl;
import com.ftg.orderservice.service.PaymentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/order-service")
@AllArgsConstructor
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	private OrderServiceImpl orderService;
	private PaymentService paymentService;

	@GetMapping("/test")
	public String cTest() {
		logger.info("Entering cTest method.");
		return "Order controller works";
	}

	@GetMapping("/test/{orderId}")
	public String test(@PathVariable String orderId) {
		logger.info("Entering test method with orderId: " + orderId);
		try {
			String initiatePayment = paymentService.initiatePayment(orderId);
			return initiatePayment;
		} catch (Exception e) {
			logger.error("An error occurred while processing the payment." + e.getMessage());
			return "Error occurred while processing the payment.";
		}
	}

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO,
			@RequestHeader("username") String username) {
		logger.info("Entering createOrder method.");
		try {
			orderDTO.setUserId(username);
			Order createdOrder = orderService.createOrder(orderDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
		} catch (Exception e) {
			logger.error("An error occurred while creating the order." + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
		logger.info("Entering getOrder method with orderId: " + orderId);
		try {
			Order order = orderService.getOrder(orderId);
			return ResponseEntity.ok(order);
		} catch (Exception e) {
			logger.error("An error occurred while fetching the order." + e.getMessage());
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
