package com.back.shared.payout.event;

import com.back.shared.payout.dto.PayoutMemberDto;
import lombok.Getter;

@Getter
public class PayoutMemberCreatedEvent {
    PayoutMemberDto payoutMemberDto;

    public PayoutMemberCreatedEvent(PayoutMemberDto payoutMemberDto) {
        this.payoutMemberDto = payoutMemberDto;
    }
}
