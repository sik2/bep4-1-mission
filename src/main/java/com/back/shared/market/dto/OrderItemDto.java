package com.back.shared.market.dto;

import com.back.standard.HasModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderItemDto implements HasModelTypeCode  {
    private final int id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
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
    private final long payoutFee;       // 플랫폼이 가져가는 수수료
    private final long salePriceWithoutFee;     // 수수료를 제외하고 판매자에게 정산할 금액

    @Override
    public String getModelTypeCode() {
        return "OrderItem";
    }
}
