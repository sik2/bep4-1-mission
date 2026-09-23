package com.back.boundedContext.market.domain;

import java.util.ArrayList;
import java.util.List;

import com.back.global.jpa.entity.BaseManualIdAndTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "MARKET_CART")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseManualIdAndTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private MarketMember buyer;

    @OneToMany(mappedBy = "cart", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private int itemsCount;

    public Cart(MarketMember buyer) {
        super(buyer.getId());
        this.buyer = buyer;
    }

    public void addItem(Product product) {
        CartItem cartItem = new CartItem(this, product);
        this.getItems().add(cartItem);
        this.itemsCount++;
    }

    public boolean hasItems() {
        return itemsCount > 0;
    }

    public void clearItems() {
        this.getItems().clear();
    }
}
