package com.ftg.orderservice.dto;

import java.util.List;

import com.ftg.orderservice.models.Payment;
import com.ftg.orderservice.rs.dto.MenuItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

	private String orderId;
	private String userId;
	private Long RestaurantId;
	private String orderStatus;
	private List<MenuItem> items;
	private Payment payment;

}
