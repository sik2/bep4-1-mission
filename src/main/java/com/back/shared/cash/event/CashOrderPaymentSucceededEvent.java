package com.back.shared.cash.event;

import com.back.shared.market.dto.OrderDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CashOrderPaymentSucceededEvent {
	private final OrderDto orderDto;
	private final long pgPaymentAmount;
}
