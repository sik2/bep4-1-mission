package com.back.boundedContext.market.domain;

import com.back.global.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MARKET_ORDER_ITEM")
@NoArgsConstructor
public class OrderItem extends BaseIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    private String productName;
    private long price;
    private long salePrice;
    private double payoutRate = MarketPolicy.PRODUCT_PAYOUT_RATE;

    public OrderItem(Order order, Product product , String productName, long price, long salePrice) {
        this.product = product;
        this.order = order;
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

    public long getPayoutFee() {
        return MarketPolicy.calculatePayoutFee(getSalePrice(), getPayoutRate());
    }

    public long getSalePriceWithoutFee() {
        return MarketPolicy.calculateSalePriceWithoutFee(getSalePrice(), getPayoutRate());
    }
}
