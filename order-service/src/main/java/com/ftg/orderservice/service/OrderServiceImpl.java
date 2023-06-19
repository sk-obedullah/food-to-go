package com.ftg.orderservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.ftg.orderservice.dto.OrderDTO;
import com.ftg.orderservice.dto.OrderItemDTO;
import com.ftg.orderservice.models.Order;
import com.ftg.orderservice.models.OrderItem;
import com.ftg.orderservice.models.Payment;
import com.ftg.orderservice.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl {

	private OrderRepository orderRepository;

	public Order createOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setOrderId(generateOrderId());

		for (OrderItemDTO itemDTO : orderDTO.getItems()) {
			OrderItem item = new OrderItem();
			item.setName(itemDTO.getName());
			item.setPrice(itemDTO.getPrice());
			order.addItem(item);
		}

		Payment payment = new Payment();
		payment.setAmount(orderDTO.getPaymentAmount());
		order.setPayment(payment);

		return orderRepository.save(order);
	}

	public Order getOrder(String orderId) {
	 Optional<Order> findByOrderId = orderRepository.findByOrderId(orderId);
		if (!findByOrderId.isPresent()) {
			throw new ResourceAccessException("");
		}
		return findByOrderId.get();
	}

	public Order updateOrder(String orderId, OrderDTO orderDTO) {
		Order order = getOrder(orderId);

		order.getItems().clear();
		for (OrderItemDTO itemDTO : orderDTO.getItems()) {
			OrderItem item = new OrderItem();
			item.setName(itemDTO.getName());
			item.setPrice(itemDTO.getPrice());
			order.addItem(item);
		}

		Payment payment = order.getPayment();
		payment.setAmount(orderDTO.getPaymentAmount());

		return orderRepository.save(order);
	}

	public void deleteOrder(String orderId) {
		Order order = getOrder(orderId);
		orderRepository.delete(order);
	}

	public List<Order> getAllOrders() {
		List<Order> findAll = orderRepository.findAll();
		return findAll;
	}

	private String generateOrderId() {
		return UUID.randomUUID().toString();
	}
}
