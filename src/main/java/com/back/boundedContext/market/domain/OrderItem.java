package com.back.boundedContext.market.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderItemDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MARKET_ORDER_ITEM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String productName;

    private long price;

    private long salePrice;

    private double payoutRate = MarketPolicy.PRODUCT_PAYOUT_RATE;

    public OrderItem(Order order, Product product, String productName, long price, long salePrice) {
        this.order = order;
        this.product = product;
        this.productName = productName;
        this.price = price;
        this.salePrice = salePrice;
    }

    public OrderItemDto toDto() {
        return new OrderItemDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                order.getId(),
                order.getBuyer().getId(),
                order.getBuyer().getNickname(),
                product.getSeller().getId(),
                product.getSeller().getNickname(),
                product.getId(),
                productName,
                price,
                salePrice,
                payoutRate,
                getPayoutFee(),
                getSalePriceWithoutFee()
        );
    }

    private long getSalePriceWithoutFee() {
        return MarketPolicy.calculateSalePriceWithoutFee(getSalePrice(), getPayoutRate());
    }

    private long getPayoutFee() {
        return MarketPolicy.calculatePayoutFee(getSalePrice(), getPayoutRate());
    }
}