package com.back.shared.payout.dto;

import java.time.LocalDateTime;

public record PayoutDto(
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        int payeeId,
        String payeeName,
        LocalDateTime payoutDate,
        long amount,
        boolean isPayeeSystem
) {

    public String getModelTypeCode() {
        return "Payout";
    }
}