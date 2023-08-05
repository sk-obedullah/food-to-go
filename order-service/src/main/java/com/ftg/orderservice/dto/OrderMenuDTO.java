package com.ftg.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderMenuDTO {

	private String restaurantId;
	private int orderId;
	private String address;
	private String amount;
	private String mode;
	private String time;
	private int[] menuItemIds;

}
