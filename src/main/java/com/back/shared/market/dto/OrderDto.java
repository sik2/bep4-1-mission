package com.back.shared.market.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDto {
	private final int id;
	private final LocalDateTime createDate;
	private final LocalDateTime modifyDate;
	private final int customerId;
	private final String customerName;
	private final long price;
	private final long salePrice;
	private final LocalDateTime requestPaymentDate;
	private final LocalDateTime paymentDate;
}
