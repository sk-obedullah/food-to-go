package com.ftg.orderservice.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders_status")
public class OrderStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String orderId;
	private String orderStatus;
	private String duration;
//	private String userName;
	//private String restaurantMealStatus;
	//private string paymentStatus;
	//private string orderDeliveryStatus;
}
