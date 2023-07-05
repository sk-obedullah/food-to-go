package com.ftg.orderservice.controller;

import java.util.List;

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

import com.ftg.orderservice.dto.OrderDTO;
import com.ftg.orderservice.dto.OrderItemDTO;
import com.ftg.orderservice.models.Order;
import com.ftg.orderservice.service.OrderServiceImpl;
import com.ftg.orderservice.service.PaymentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/order-service")
@AllArgsConstructor
public class OrderController {

	private OrderServiceImpl orderService;
	
	private PaymentService paymentService;

	@GetMapping("/test/{orderId}")
	public String test(@PathVariable String orderId) {
		String initiatePament = paymentService.initiatePament(orderId);
		return initiatePament;
	}

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
		Order createdOrder = orderService.createOrder(orderDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
		Order order = orderService.getOrder(orderId);
		return ResponseEntity.ok(order);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<Order> updateOrder(@PathVariable String orderId, @RequestBody OrderDTO orderDTO) {
		Order updatedOrder = orderService.updateOrder(orderId, orderDTO);
		return ResponseEntity.ok(updatedOrder);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
		orderService.deleteOrder(orderId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}

}
