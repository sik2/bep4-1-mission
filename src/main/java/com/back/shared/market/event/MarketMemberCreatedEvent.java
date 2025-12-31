package com.back.shared.market.event;

import com.back.shared.market.dto.MarketMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MarketMember 생성 이벤트
 *
 * [설계 원칙]
 * - 이벤트는 "무슨 일이 일어났는가"만 표현
 * - MarketMember 데이터는 도메인 기반 공유 DTO(MarketMemberDto)를 사용
 */
@Getter
@AllArgsConstructor
public class MarketMemberCreatedEvent {
    private final MarketMemberDto member;
}
