package com.back.shared.cash.event;

import com.back.shared.cash.dto.CashMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CashMember 생성 이벤트
 *
 * [설계 원칙]
 * - 이벤트는 "무슨 일이 일어났는가"만 표현
 * - CashMember 데이터는 도메인 기반 공유 DTO(CashMemberDto)를 사용
 */
@Getter
@AllArgsConstructor
public class CashMemberCreatedEvent {
    private final CashMemberDto member;
}
