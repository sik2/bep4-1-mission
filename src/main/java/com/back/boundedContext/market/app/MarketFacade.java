package com.back.boundedContext.market.app;

import com.back.global.response.RsData;
import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.domain.Product;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketFacade {
    private final MarketSyncMemberUseCase memberUseCase;
    private final MarketSupport marketSupport;

    private final MarketCreateProductUseCase marketCreateProductUseCase;
    private final MarketCreateCartUseCase marketCreateCartUseCase;
    private final MarketCreateOrderUseCase marketCreateOrderUseCase;
    private final MarketCompleteOrderPaymentUseCase marketCompleteOrderPaymentUseCase;
    private final MarketCancelOrderRequestPaymentUseCase marketCancelOrderRequestPaymentUseCase;

    @Transactional
    public RsData<Order> createOrder(Cart cart) {
        return marketCreateOrderUseCase.createOrder(cart);
    }

    @Transactional(readOnly = true)
    public long ordersCount() {
        return marketSupport.ordersCount();
    }

    @Transactional(readOnly = true)
    public long productsCount() {
        return marketSupport.productsCount();
    }

    @Transactional
    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketSupport.findMemberByUsername(username);
    }

    @Transactional
    public MarketMember syncMember(MemberDto member) {
        return memberUseCase.syncMember(member);
    }

    @Transactional(readOnly = true)
    public Optional<Cart> findCartByBuyer(MarketMember member) {
        return marketSupport.findCartByBuyer(member);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(int id) {
        return marketSupport.findProductById(id);
    }


    @Transactional
    public Product createProduct(
            MarketMember seller,
                                 String sourceTypeCode,
                                 int sourceId,
                                 String name,
                                 String description,
                                 long price,
                                 long salePrice) {
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

    @Transactional
    public Cart createCart(MarketMemberDto memberDto) {
        return marketCreateCartUseCase.createCart(memberDto);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findOrderById(int id) {
        return marketSupport.findOrderById(id);
    }

    @Transactional
    public void requestPayment(Order order, long pgPaymentAmount) {
        order.requestPayment(pgPaymentAmount);
    }

    @Transactional
    public void completeOrderPayment(int orderId) {
        marketCompleteOrderPaymentUseCase.completePayment(orderId);
    }

    @Transactional
    public void cancelOrderRequestPayment(int orderId) {
        marketCancelOrderRequestPaymentUseCase.cancelRequestPayment(orderId);
    }
}
