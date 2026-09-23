package com.back.shared.market.event;

import com.back.shared.market.dto.OrderDto;
import lombok.Getter;

@Getter
public class MarketOrderPaymentCompletedEvent {
    OrderDto orderDto;

    public MarketOrderPaymentCompletedEvent(OrderDto orderDto) {
        this.orderDto = orderDto;
    }
}
