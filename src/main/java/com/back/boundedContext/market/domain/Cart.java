package com.back.boundedContext.market.domain;

import com.back.global.entity.BaseManualIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "MARKET_CART")
@NoArgsConstructor
public class Cart extends BaseManualIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private MarketMember buyer;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private int itemsCount;

    public boolean hasItems() {
        return itemsCount > 0;
    }

    public Cart(MarketMember buyer) {
        super(buyer.getId());
        this.buyer = buyer;
    }

    public void addItem(Product product) {
        CartItem cartItem = new CartItem(this, product);
        items.add(cartItem);
        itemsCount++;
    }

    public void clearItems() {
        items.clear();
        itemsCount = 0;
    }
}
