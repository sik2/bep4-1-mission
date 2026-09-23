package com.back.shared.payout.dto;

import java.time.LocalDateTime;

import com.back.standard.modelType.HasModelTypeCode;

public record PayoutDto(int id, LocalDateTime createDate, LocalDateTime modifyDate, int payeeId,
                        String payeeName, LocalDateTime payoutDate, long amount, boolean isPayeeSystem)
        implements HasModelTypeCode {
    @Override
    public String getModelTypeCode() {
        return "Payout";
    }
}
