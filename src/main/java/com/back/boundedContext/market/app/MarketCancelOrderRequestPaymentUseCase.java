package com.back.boundedContext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketCancelOrderRequestPaymentUseCase {
	private final OrderRepository orderRepository;

	public void cancelRequestPayment(int orderId) {
		Order order = orderRepository.findById(orderId).get();

		order.cancelRequestPayment();
	}
}
