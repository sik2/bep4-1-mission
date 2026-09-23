package com.back.boundedContext.market.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.domain.Product;
import com.back.global.rsData.RsData;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketFacade {
    private final MarketMemberSyncUseCase marketMemberSyncUseCase;
    private final MarketCreateProductUseCase marketCreateProductUseCase;
    private final MarketSupport marketSupport;
    private final MarketCreateCartUseCase marketCreateCartUseCase;
    private final MarketCreateOrderUserCase marketCreateOrderUseCase;
    private final MarketCompleteOrderPaymentUseCase marketCompleteOrderPaymentUseCase;
    private final MarketCancelOrderPaymentUseCase marketCancelOrderPaymentUseCase;

    public void syncMember(MemberDto memberDto) {
        marketMemberSyncUseCase.syncMember(memberDto);
    }

    public Product createProduct(MarketMember seller,
                                 String sourceTypeCode,
                                 Integer sourceId,
                                 String name,
                                 String description,
                                 Long price,
                                 Long salePrice) {
        return marketCreateProductUseCase.createProduct(seller, sourceTypeCode, sourceId, name,
                                                        description, price, salePrice);
    }

    @Transactional(readOnly = true)
    public long productsCount() {
        return marketSupport.countProducts();
    }

    @Transactional(readOnly = true)
    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketSupport.findMemberByUsername(username);
    }

    public RsData<Cart> createCart(MarketMemberDto member) {
        return marketCreateCartUseCase.createCart(member);
    }

    public Optional<Product> findProductById(int id) {
        return marketSupport.findById(id);
    }

    public Optional<Cart> findCartByBuyer(MarketMember buyer) {
        return marketSupport.findCartByBuyer(buyer);
    }

    @Transactional(readOnly = true)
    public long ordersCount() {
        return marketSupport.countOrders();
    }

    @Transactional
    public RsData<Order> createOrder(Cart cart) {
        return marketCreateOrderUseCase.createOrder(cart);
    }

    @Transactional
    public void requestPayment(Order order, long pgPaymentAmount) {
        order.requestPayment(pgPaymentAmount);
    }

    @Transactional
    public Optional<Order> findOrderById(int id) {
        return marketSupport.findOrderById(id);
    }

    public void completeOrderPayment(int orderId) {
        marketCompleteOrderPaymentUseCase.completeOrderPayment(orderId);
    }

    public void cancelOrderRequestPayment(int orderId) {
        marketCancelOrderPaymentUseCase.cancelOrderRequestPayment(orderId);
    }
}
