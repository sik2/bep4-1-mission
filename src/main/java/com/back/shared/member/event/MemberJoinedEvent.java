package com.back.shared.member.event;

import com.back.shared.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원 가입 이벤트
 *
 * [설계 원칙]
 * - 이벤트는 "무슨 일이 일어났는가"만 표현
 * - 회원 데이터는 도메인 기반 공유 DTO(MemberDto)를 사용
 * - 도메인 일관성 > 이벤트 책임분리: MemberUpdatedEvent와 동일한 MemberDto 사용
 *
 * [수신 컨텍스트]
 * - Cash: CashMember 생성 + Wallet 생성
 * - Market: MarketMember 생성 + Cart 생성
 * - Post: PostMember 생성
 */
@Getter
@AllArgsConstructor
public class MemberJoinedEvent {
    private final MemberDto member;
}
