package com.ftg.orderservice.rs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

	private Long id;

	private String name;

	private double price;

	private String imageUrl;

	private String category;

	private String description;

}
