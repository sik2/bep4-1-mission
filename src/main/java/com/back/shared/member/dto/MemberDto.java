package com.back.shared.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Member 도메인의 공유 DTO
 *
 * [설계 원칙]
 * - 도메인 개념(Member) 기반의 공유 DTO로, 여러 이벤트에서 재사용
 * - 비밀번호 등 민감정보는 제외
 *
 * [새 Payload/DTO를 정의해야 하는 경우]
 * 1. 이벤트별 추가 데이터가 필요한 경우
 * 2. 민감정보를 제외해야 하는 경우 (이미 적용됨)
 * 3. 이벤트마다 필요한 필드가 현저히 다른 경우
 *
 * [사용 이벤트]
 * - MemberJoinedEvent: 회원 가입
 * - MemberUpdatedEvent: 회원 정보 수정
 */
@Getter
@AllArgsConstructor
public class MemberDto {
    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String username;
    private final String nickname;
    private final int activityScore;

}

