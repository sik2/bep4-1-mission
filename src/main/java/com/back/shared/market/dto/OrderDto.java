package com.back.shared.market.dto;

import java.time.LocalDateTime;

import com.back.standard.modelType.CanGetModelTypeCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDto implements CanGetModelTypeCode {
	private final int id;
	private final LocalDateTime createTime;
	private final LocalDateTime modifyTime;
	private final int customerId;
	private final String customerName;
	private final long price;
	private final long salePrice;
	private final LocalDateTime requestPaymentTime;
	private final LocalDateTime paymentTime;

	@Override
	public String getModelTypeCode() {
		return "Order";
	}
}
