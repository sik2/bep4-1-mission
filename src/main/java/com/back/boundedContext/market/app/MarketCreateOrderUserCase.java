package com.back.boundedContext.market.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.OrderRepository;
import com.back.global.rsData.RsData;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketCreateOrderUserCase {
    private final OrderRepository orderRepository;

    public RsData<Order> createOrder(Cart cart) {
        Order _order = new Order(cart);

        Order order = orderRepository.save(_order);

        cart.clearItems();

        return new RsData<>(
                "201-1",
                "%d번 주문이 생성되었습니다.".formatted(order.getId()),
                order
        );
    }
}
