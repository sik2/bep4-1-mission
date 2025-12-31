package com.back.shared.market.dto;

import java.time.LocalDateTime;

import com.back.standard.modelType.CanGetModelTypeCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItemDto implements CanGetModelTypeCode {
	private final int id;
	private final LocalDateTime createTime;
	private final LocalDateTime modifyTime;
	private final int orderId;
	private final int buyerId;
	private final String buyerName;
	private final int sellerId;
	private final String sellerName;
	private final int productId;
	private final String productName;
	private final long price;
	private final long salePrice;
	private final double payoutRate;
	private final long payoutFee;
	private final long salePriceWithoutFee;

	@Override
	public String getModelTypeCode() {
		return "OrderItem";
	}
}
