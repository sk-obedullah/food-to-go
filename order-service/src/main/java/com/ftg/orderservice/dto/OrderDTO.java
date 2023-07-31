package com.ftg.orderservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	
	private String orderId;
	private String userId;
	private Long RestaurantId;
	private String orderStatus;
	private List<OrderItemDTO> items;


}
