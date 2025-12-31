package com.back.shared.payout.dto;


import com.back.standard.modelType.CanGetModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PayoutDto implements CanGetModelTypeCode {
    private final Long id;

    private final LocalDateTime createdAt;
    private final LocalDateTime UpdatedAt;

    private Long payeeId;
    private String payeeName;
    private LocalDateTime payoutDate;
    private long amount;
    private boolean isPayeeSystem;

    @Override
    public String getModelTypeCode() {
        return "Payout";
    }
}