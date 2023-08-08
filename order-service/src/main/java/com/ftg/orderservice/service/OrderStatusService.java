package com.ftg.orderservice.service;

import org.springframework.stereotype.Service;

import com.ftg.orderservice.dto.StatusUpdateDTO;
import com.ftg.orderservice.models.OrderStatus;
import com.ftg.orderservice.repository.OrderStatusRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderStatusService {

	private OrderStatusRepository orderStatusRepository;

	public OrderStatus addStatus(OrderStatus status) {
		OrderStatus save = orderStatusRepository.save(status);
		return save;
	}

	public OrderStatus updateStatus(StatusUpdateDTO statusDTO) {
		OrderStatus findByOrderId = orderStatusRepository.findByOrderId(statusDTO.getOrderId());
		findByOrderId.setOrderStatus(statusDTO.getUpdate());
		findByOrderId.setDuration(statusDTO.getDuration());
		OrderStatus save = orderStatusRepository.save(findByOrderId);
		return save;
	}
}
