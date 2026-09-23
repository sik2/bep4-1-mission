package com.back.shared.market.event;

import com.back.shared.market.dto.MarketMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MarketMemberCreatedEvent {
    MarketMemberDto member;
}
