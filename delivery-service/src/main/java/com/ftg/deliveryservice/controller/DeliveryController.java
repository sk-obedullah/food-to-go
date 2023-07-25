package com.ftg.deliveryservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/delivery-service")
public class DeliveryController {

	@GetMapping("/test")
	public String cTest() {
		return "Delivery controller works";
	}
}
