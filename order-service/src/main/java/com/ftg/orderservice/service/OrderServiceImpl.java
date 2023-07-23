package com.ftg.orderservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.ftg.orderservice.dto.OrderDTO;
import com.ftg.orderservice.dto.OrderItemDTO;
import com.ftg.orderservice.ecxception.ResourceNotFoundException;
import com.ftg.orderservice.models.Order;
import com.ftg.orderservice.models.OrderItem;
import com.ftg.orderservice.models.Payment;
import com.ftg.orderservice.repository.OrderRepository;
import com.ftg.orderservice.utils.Constants;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl {

	private OrderRepository orderRepository;
	
	ModelMapper modelMapper;

	public Order createOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setOrderId(generateOrderId());

		for (OrderItemDTO itemDTO : orderDTO.getItems()) {
			OrderItem item = new OrderItem();
			item.setName(itemDTO.getName());
			item.setPrice(itemDTO.getPrice());
			order.addItem(item);
		}
		order.setStatus(Constants.ORDER_CREATED);
		Payment payment = new Payment();
		payment.setAmount(calculate_total_price(orderDTO.getItems()));
		payment.setPaymentStatus(Constants.PAYMENT_PENDING);
		payment.setTransactionId("");
		order.setPayment(payment);
		order.setUserId(1L);
		order.setTotalAmount(payment.getAmount());

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
		payment.setAmount(calculate_total_price(orderDTO.getItems()));
		payment.setPaymentStatus(Constants.PAYMENT_PENDING);

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

	public Payment updatePayment(String orderId,Payment updatedPayment) {

		Order order = orderRepository.findByOrderId(orderId).orElseThrow(()->new ResourceNotFoundException("Order", "orderId", orderId));
		Payment payment = order.getPayment();
		payment.setPaymentStatus(updatedPayment.getPaymentStatus());
		payment.setTransactionId(updatedPayment.getTransactionId());
		order.setPayment(payment);
		orderRepository.save(order);
		return payment;
	}

	private String generateOrderId() {
		return UUID.randomUUID().toString();
	}

	private double calculate_total_price(List<OrderItemDTO> items) {
		return items.stream().mapToDouble(OrderItemDTO::getPrice).sum();
	}
}
