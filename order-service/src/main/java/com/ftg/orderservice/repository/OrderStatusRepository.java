package com.ftg.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftg.orderservice.models.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

	OrderStatus findByOrderId(String orderId);
	List<OrderStatus> findByOrderStatus(String status);
}
