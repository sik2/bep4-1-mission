package com.back.boundedContext.market.app.usecase;

import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCompleteOrderPaymentUseCase {
    private final OrderRepository orderRepository;

    public void completePayment(Long orderId) {
        Order order = orderRepository.findById(orderId).get();

        order.completePayment();
    }
}