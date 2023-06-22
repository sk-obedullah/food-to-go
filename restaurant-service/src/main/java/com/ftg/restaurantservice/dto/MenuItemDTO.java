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

	private String name;

	private double price;

	private String imageUrl;

	private String Category;

	private String Description;
}
