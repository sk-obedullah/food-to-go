package com.ftg.orderservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	private OrderRepository orderRepository;
	private ModelMapper modelMapper;

	public Order createOrder(OrderDTO orderDTO) {
		logger.info("Creating order...");
		try {
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
			payment.setAmount(calculateTotalPrice(orderDTO.getItems()));
			payment.setPaymentStatus(Constants.PAYMENT_PENDING);
			payment.setTransactionId("");
			order.setPayment(payment);

			order.setUserId(orderDTO.getUserId());
			order.setTotalAmount(payment.getAmount());

			return orderRepository.save(order);
		} catch (Exception e) {
			logger.error("Error occurred while creating the order.", e);
			throw e;
		}
	}

	public Order getOrder(String orderId) {
		logger.info("Getting order with orderId: {}", orderId);
		try {
			Optional<Order> findByOrderId = orderRepository.findByOrderId(orderId);
			if (!findByOrderId.isPresent()) {
				logger.error("Order with orderId: {} not found", orderId);
				throw new ResourceAccessException("");
			}
			return findByOrderId.get();
		} catch (Exception e) {
			logger.error("Error occurred while fetching the order.", e);
			throw e;
		}
	}

	public Order updateOrder(String orderId, OrderDTO orderDTO) {
		logger.info("Updating order with orderId: {}", orderId);
		try {
			Order order = getOrder(orderId);

			order.getItems().clear();
			for (OrderItemDTO itemDTO : orderDTO.getItems()) {
				OrderItem item = new OrderItem();
				item.setName(itemDTO.getName());
				item.setPrice(itemDTO.getPrice());
				order.addItem(item);
			}

			Payment payment = order.getPayment();
			payment.setAmount(calculateTotalPrice(orderDTO.getItems()));
			payment.setPaymentStatus(Constants.PAYMENT_PENDING);

			return orderRepository.save(order);
		} catch (Exception e) {
			logger.error("Error occurred while updating the order.", e);
			throw e;
		}
	}

	public void deleteOrder(String orderId) {
		logger.info("Deleting order with orderId: {}", orderId);
		try {
			Order order = getOrder(orderId);
			orderRepository.delete(order);
		} catch (Exception e) {
			logger.error("Error occurred while deleting the order.", e);
			throw e;
		}
	}

	public List<Order> getAllOrders() {
		logger.info("Getting all orders...");
		try {
			List<Order> findAll = orderRepository.findAll();
			return findAll;
		} catch (Exception e) {
			logger.error("Error occurred while fetching all orders.", e);
			throw e;
		}
	}

	public Payment updatePayment(String orderId, Payment updatedPayment) {
		logger.info("Updating payment for order with orderId: {}", orderId);
		try {
			Order order = orderRepository.findByOrderId(orderId)
					.orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));

			Payment payment = order.getPayment();
			payment.setPaymentStatus(updatedPayment.getPaymentStatus());
			payment.setTransactionId(updatedPayment.getTransactionId());

			order.setPayment(payment);
			orderRepository.save(order);

			return payment;
		} catch (Exception e) {
			logger.error("Error occurred while updating the payment.", e);
			throw e;
		}
	}

	private String generateOrderId() {
		return UUID.randomUUID().toString();
	}

	private double calculateTotalPrice(List<OrderItemDTO> items) {
		return items.stream().mapToDouble(OrderItemDTO::getPrice).sum();
	}
}
