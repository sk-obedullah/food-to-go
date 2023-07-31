package com.ftg.orderservice.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ftg.orderservice.ecxception.ResourceNotFoundException;
import com.ftg.orderservice.models.Order;
import com.ftg.orderservice.models.Payment;
import com.ftg.orderservice.repository.OrderRepository;
import com.ftg.orderservice.utils.Constants;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	private OrderRepository orderRepository;
	private OrderServiceImpl orderServiceImpl;
	private ModelMapper modelMapper;

	public String initiatePayment(String orderId) {
		logger.info("Initiating payment for orderId: {}", orderId);
		try {
			Payment payment = pay(orderId);
			return payment.getPaymentStatus().equalsIgnoreCase(Constants.PAYMENT_SUCCESS) ? payment.getTransactionId()
					: "";
		} catch (Exception e) {
			logger.error("Error occurred while initiating payment for orderId: {}", orderId, e);
			return "";
		}
	}

	private Payment pay(String orderId) {
		Order order = orderRepository.findByOrderId(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "OrderId", orderId));

		Payment payment = order.getPayment();
		double totalAmount = payment.getAmount();
		String userId = order.getUserId();

		String transactionId = makePayment(totalAmount, userId, orderId);

		if (transactionId.trim().length() > 0) {
			payment.setPaymentStatus(Constants.PAYMENT_SUCCESS);
			payment.setTransactionId(transactionId);
			order.setPayment(payment);
			order.setStatus(Constants.ORDER_CONFIRMED);
			orderServiceImpl.updatePayment(orderId, payment);
			return payment;
		}

		payment.setPaymentStatus(Constants.PAYMENT_FAILED);
		order.setStatus(Constants.ORDER_FAILED);
		return payment;
	}

	private String makePayment(double amount, String userId, String orderId) {
		// Implementation of the payment logic goes here.
		// It should handle the payment process and return the transaction ID.
		// This is just a placeholder return for the sake of example.
		Integer hashCode = userId.hashCode();
		return hashCode.toString();
	}
}
