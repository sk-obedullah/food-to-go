package com.ftg.orderservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderMenuDTO {

	private String userId;
	private String restaurantId;
	private String orderId;
	private String address;
	private String amount;
	private String mode;
	private String time;
	private List<Long> menuItemIds;

}
