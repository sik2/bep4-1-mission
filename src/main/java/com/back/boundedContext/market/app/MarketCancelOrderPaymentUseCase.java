package com.back.boundedContext.market.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.OrderRepository;
import com.back.shared.market.dto.OrderDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketCancelOrderPaymentUseCase {
    private final OrderRepository orderRepository;

    public void cancelOrderRequestPayment(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        order.cancelRequestPayment();
    }
}
