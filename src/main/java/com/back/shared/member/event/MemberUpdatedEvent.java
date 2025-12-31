package com.back.shared.member.event;

import com.back.shared.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원 정보 수정 이벤트 (주로 activityScore 변경 시 발행)
 *
 * [설계 원칙]
 * - 이벤트는 "무슨 일이 일어났는가"만 표현
 * - 회원 데이터는 도메인 기반 공유 DTO(MemberDto)를 사용
 * - 도메인 일관성 > 이벤트 책임분리: MemberJoinedEvent와 동일한 MemberDto 사용
 *
 * [발행 시점]
 * - Member.increasePoint() 호출 시 도메인에서 자동 발행
 *
 * [수신 컨텍스트]
 * - Cash: CashMember.activityScore 갱신
 * - Market: MarketMember.activityScore 갱신
 * - Post: PostMember.activityScore 갱신
 */
@Getter
@AllArgsConstructor
public class MemberUpdatedEvent {
    private final MemberDto member;
}
