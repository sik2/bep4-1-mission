package com.back.boundedContext.market.app.facade;

import com.back.boundedContext.market.app.Query.MarketQuery;
import com.back.boundedContext.market.app.usecase.*;
import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.domain.Product;
import com.back.global.rsData.RsData;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketFacade {

    private final MarketSyncMemberUseCase marketSyncMemberUseCase;
    private final MarketQuery marketQuery;

    private final MarketCreateProductUseCase marketCreateProductUseCase;
    private final MarketCreateCartUseCase marketCreateCartUseCase;
    private final MarketCreateOrderUseCase marketCreateOrderUseCase;

    private final MarketCompleteOrderPaymentUseCase marketCompleteOrderPaymentUseCase;
    private final MarketCancelOrderRequestPaymentUseCase marketCancelOrderRequestPaymentUseCase;

    @Transactional
    public MarketMember syncMember(MemberDto member) {
        return marketSyncMemberUseCase.syncMember(member);
    }


    @Transactional(readOnly = true)
    public long productsCount() {
        return marketQuery.countProducts();
    }

    @Transactional
    public Product createProduct(
            MarketMember seller,
            String sourceTypeCode,
            Long sourceId,
            String name,
            String description,
            long price,
            long salePrice
    ) {

        return marketCreateProductUseCase.createProduct(
                seller,
                sourceTypeCode,
                sourceId,
                name,
                description,
                price,
                salePrice
        );
    }

    @Transactional(readOnly = true)
    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketQuery.findMemberByUsername(username);
    }


    @Transactional
    public RsData<Cart> createCart(MarketMemberDto buyer) {
        return marketCreateCartUseCase.createCart(buyer);
    }

    @Transactional(readOnly = true)
    public Optional<Cart> findCartByBuyer(MarketMember buyer) {
        return marketQuery.findCartByBuyer(buyer);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(Long id) {
        return marketQuery.findProductById(id);
    }

    @Transactional(readOnly = true)
    public long ordersCount() {
        return marketQuery.countOrders();
    }

    @Transactional
    public RsData<Order> createOrder(Cart cart) {
        return marketCreateOrderUseCase.createOrder(cart);
    }


    @Transactional(readOnly = true)
    public Optional<Order> findOrderById(Long id) {
        return marketQuery.findOrderById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findOrderByIdWithBuyer(Long id) {
        Optional<Order> orderOpt = marketQuery.findOrderById(id);
        // readOnly 트랜잭션 내에서 buyer 프록시 초기화
        orderOpt.ifPresent(order -> order.getBuyer().getNickname());
        return orderOpt;
    }

    @Transactional
    public void requestPayment(Order order, long pgPaymentAmount) {
        // buyer는 이미 초기화되어 있어야 함
        order.requestPayment(pgPaymentAmount);
    }

    @Transactional
    public void completeOrderPayment(Long orderId) {
        marketCompleteOrderPaymentUseCase.completePayment(orderId);
    }

    @Transactional
    public void cancelOrderRequestPayment(Long orderId) {
        marketCancelOrderRequestPaymentUseCase.cancelRequestPayment(orderId);
    }

}
