package com.back.boundedContext.market.app;

import com.back.global.response.RsData;
import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarketCreateOrderUseCase {
    private final OrderRepository orderRepository;

    @Transactional
    public RsData<Order> createOrder(Cart cart) {
        Order order = new Order(cart);

        Order _order = orderRepository.save(order);

        cart.clearItems();

        return new RsData<>(
                "201-1",
                "%d번 주문이 생성되었습니다.".formatted(order.getId()),
                _order
        );
    }
}
