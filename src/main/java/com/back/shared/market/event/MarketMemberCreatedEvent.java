package com.back.shared.market.event;

import com.back.shared.market.dto.MarketMemberDto;
import lombok.Getter;

@Getter
public class MarketMemberCreatedEvent {
    MarketMemberDto memberDto;

    public MarketMemberCreatedEvent(MarketMemberDto memberDto) {
        this.memberDto = memberDto;
    }
}
