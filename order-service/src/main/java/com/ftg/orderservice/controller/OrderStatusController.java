package com.ftg.orderservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftg.orderservice.dto.StatusUpdateDTO;
import com.ftg.orderservice.models.OrderStatus;
import com.ftg.orderservice.repository.OrderStatusRepository;
import com.ftg.orderservice.service.OrderStatusService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/order/order-service")
@AllArgsConstructor
public class OrderStatusController {

	private OrderStatusService orderStatusService;
	
	@PostMapping("/update")
	public ResponseEntity<?> updateOrderStatus(@RequestBody StatusUpdateDTO updates){
		OrderStatus updateStatus = orderStatusService.updateStatus(updates);
		return ResponseEntity.ok(updateStatus);
	}
}
