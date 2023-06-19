package com.ftg.orderservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftg.orderservice.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	 Optional<Order> findByOrderId(String orderId);
}
