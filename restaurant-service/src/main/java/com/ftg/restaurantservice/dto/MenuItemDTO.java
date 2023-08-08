package com.ftg.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {

	private Long id;
	
	private int restaurantId;

	private String itemName;

	private String itemPrice;

	private String itemImage;
	
	private String itemPrepTime;

	private String itemType;

	private String itemDescription;
}
