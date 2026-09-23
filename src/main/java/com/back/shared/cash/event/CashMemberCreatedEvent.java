package com.back.shared.cash.event;

import com.back.shared.cash.dto.CashMemberDto;
import lombok.Getter;

@Getter
public class CashMemberCreatedEvent {
    CashMemberDto cashMemberDto;

    public CashMemberCreatedEvent(CashMemberDto cashMemberDto) {
        this.cashMemberDto = cashMemberDto;
    }
}
