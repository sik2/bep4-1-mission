package com.back.boundedContext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="MARKET_ORDER")
@Getter
@NoArgsConstructor
public class Order extends BaseIdAndTime {
    @ManyToOne(fetch = LAZY)
    private MarketMember buyer;
    private LocalDateTime requestPaymentDate;
    private LocalDateTime paymentDate;
    private LocalDateTime cancelDate;
    private long price;
    private long salePrice;

    @OneToMany(mappedBy = "order", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(Cart cart) {
        this.buyer = cart.getBuyer();

        cart.getItems().forEach(item -> {
            addItem(item.getProduct());
        });
    }

    public void addItem(Product product) {
        OrderItem orderItem = new OrderItem(
            this,
            product,
            product.getName(),
            product.getPrice(),
            product.getSalePrice()
        );

        items.add(orderItem);

        price += product.getPrice();
        salePrice += product.getSalePrice();
    }

    public void completePayment() {
        paymentDate = LocalDateTime.now();

        publishEvent(
            new MarketOrderPaymentCompletedEvent(
                this.toDto()
            )
        );
    }

    public boolean isPaid() {
        return paymentDate != null;
    }

    public void requestPayment(long pgPaymentAmount) {
        requestPaymentDate = LocalDateTime.now();

        publishEvent(
                new MarketOrderPaymentRequestedEvent(this.toDto(), pgPaymentAmount)
        );
    }

    public void cancelRequestPayment() {
        requestPaymentDate = null;
    }

    public boolean isPaymentInProgress() {
        // 결제 요청이 들어왔으나, 결제완료나 취소 날짜가 안찍힌 경우
        return requestPaymentDate != null && paymentDate == null && cancelDate == null;
    }

    public boolean isCanceled() {
        return cancelDate != null;
    }

    public OrderDto toDto() {
        return new OrderDto(
            getId(),
            getCreateDate(),
            getModifyDate(),
            buyer.getId(),
            buyer.getNickname(),
            price,
            salePrice,
            requestPaymentDate,
            paymentDate
        );
    }
}
