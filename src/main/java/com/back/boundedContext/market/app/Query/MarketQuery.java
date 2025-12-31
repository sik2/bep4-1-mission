package com.back.boundedContext.market.app.Query;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.domain.Product;
import com.back.boundedContext.market.out.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Market 컨텍스트의 조회 서비스
 *
 * <p>[설계 원칙]
 * - Repository를 직접 노출하지 않고 필요한 조회 메서드만 제공
 * - 복잡한 조회 로직이나 fetch join은 여기서 처리
 */
@Component
@RequiredArgsConstructor
public class MarketQuery {
    private final com.back.boundedContext.market.out.repository.MarketMemberRepository marketMemberRepository;
    private final com.back.boundedContext.market.out.repository.ProductRepository productRepository;
    private final com.back.boundedContext.market.out.repository.CartRepository cartRepository;
    private final OrderRepository orderRepository;


    public long countProducts() {
        return productRepository.count();
    }

    public java.util.Optional<com.back.boundedContext.market.domain.MarketMember> findMemberByUsername(String username) {
        return marketMemberRepository.findByUsername(username);
    }


    public Optional<Cart> findCartByBuyer(MarketMember buyer) {
        return cartRepository.findByBuyer(buyer);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public long countOrders() {
        return orderRepository.count();
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
