package com.back.boundedContext.market.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.OrderRepository;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.OrderDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketCompleteOrderPaymentUseCase {
    private final OrderRepository orderRepository;

    public void completeOrderPayment(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        order.completePayment();
    }
}
