package com.back.shared.market.dto;

import java.time.LocalDateTime;

import com.back.standard.modelType.HasModelTypeCode;

public record OrderDto (
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        int customerId,
        String customerName,
        long price,
        long salePrice,
        LocalDateTime requestPaymentDate,
        LocalDateTime paymentDate) implements HasModelTypeCode {

    @Override
    public String getModelTypeCode() {
        return "Order";
    }
}
