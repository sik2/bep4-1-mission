package com.back.boundedContext.market.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MARKET_ORDER")
@NoArgsConstructor
@Getter
public class Order extends BaseIdAndTime {
	@ManyToOne(fetch = FetchType.LAZY)
	private MarketMember buyer;
	private LocalDateTime cancelTime;
	private LocalDateTime requestPaymentTime;
	private LocalDateTime paymentTime;
	private long price;
	private long salePrice;

	@OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();

	public Order(Cart cart) {
		this.buyer = cart.getBuyer();

		cart.getItems().forEach(item -> {
			addItem(item.getProduct());
		});
	}

	public OrderDto toDto() {
		return new OrderDto(
			getId(),
			getCreateTime(),
			getModifyTime(),
			buyer.getId(),
			buyer.getNickName(),
			price,
			salePrice,
			requestPaymentTime,
			paymentTime
		);
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
		paymentTime = LocalDateTime.now();
		publishEvent(
			new MarketOrderPaymentCompletedEvent(
				toDto()
			)
		);
	}

	public boolean isPaid() {
		return paymentTime != null;
	}

	public void requestPayment(long pgPaymentAmount) {
		requestPaymentTime = LocalDateTime.now();

		publishEvent(
			new MarketOrderPaymentRequestedEvent(
				toDto(),
				pgPaymentAmount
			)
		);
	}

	public void cancelRequestPayment() {
		requestPaymentTime = null;
	}

	public boolean isCanceled() {
		return cancelTime != null;
	}

	public boolean isPaymentInProgress() {
		return requestPaymentTime != null && paymentTime == null && cancelTime == null;
	}
}
