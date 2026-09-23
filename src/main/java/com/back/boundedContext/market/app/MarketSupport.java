package com.back.boundedContext.market.app;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.domain.Product;
import com.back.boundedContext.market.out.CartRepository;
import com.back.boundedContext.market.out.MarketMemberRepository;
import com.back.boundedContext.market.out.OrderRepository;
import com.back.boundedContext.market.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketSupport {
    private final MarketMemberRepository marketMemberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public long ordersCount() {
        return orderRepository.count();
    }

    @Transactional(readOnly = true)
    public long productsCount() {
        return productRepository.count();
    }

    @Transactional(readOnly = true)
    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketMemberRepository.findMarketMemberByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Cart> findCartByBuyer(MarketMember member) {
        return cartRepository.findById(member.getId());
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(int id) {
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findOrderById(int id) {
        return orderRepository.findOrderById(id);
    }
}
