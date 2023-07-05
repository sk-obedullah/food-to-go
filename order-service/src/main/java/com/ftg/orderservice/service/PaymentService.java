package com.ftg.orderservice.service;

import org.modelmapper.ModelMapper;
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

	OrderRepository orderRepository;
	
	OrderServiceImpl orderServiceImpl;
	
	ModelMapper modelMapper;
	
	public String initiatePament(String OrderId) {
		Payment pay = pay(OrderId);
		
		return pay.getPaymentStatus().equalsIgnoreCase(Constants.PAYMENT_SUCCESS)? pay.getTransactionId():"";
	}

	private Payment pay(String orderId) {
		Order order = orderRepository.findByOrderId(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "OrderId", orderId));
		Payment payment = order.getPayment();
		double totalAmount =payment.getAmount();
		Long userId = order.getUserId();

		String transactionId = makePayment(totalAmount, userId, orderId);
		if (transactionId.trim().length() > 0) {
			payment.setPaymentStatus(Constants.PAYMENT_SUCCESS);
			payment.setTransactionId(transactionId);
			order.setPayment(payment);
			 orderServiceImpl.updatePayment(orderId, payment);
			return payment;
		}
		payment.setPaymentStatus(Constants.PAYMENT_FAILED);
		return payment;
	}
	
	
	private String makePayment(double amoount, Long userId, String orderId) {
		// get user account
		// check balance
		// debit from user account
		// generate a transaction id
		// add that transaction id to transactions table with all the details(orderId userId amount and transactionId)
		// get order details
		// get reatauranId from orderdetails
		// get restaurant account
		// credit the total amount to restaurant account
		//update transaction table for restaurant as well
		return "12vduvkkuge1324";

	}
}
