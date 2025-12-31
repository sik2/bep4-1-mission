package com.back.boundedContext.market.app;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCreateOrderUseCase {
    private final OrderRepository orderRepository;

    public Order createOrder(Cart cart) {
        Order order = new Order(cart);

        Order rsOrder = orderRepository.save(order);

        cart.clearItems();

        return rsOrder;
    }
}
