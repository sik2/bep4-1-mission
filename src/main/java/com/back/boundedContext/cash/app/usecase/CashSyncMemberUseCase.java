package com.back.boundedContext.cash.app.usecase;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.out.repository.CashMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.cash.event.CashMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member를 Cash 컨텍스트의 CashMember로 동기화하는 UseCase
 *
 * [설계 원칙]
 * - MemberJoinedEvent, MemberUpdatedEvent 모두 동일한 MemberDto를 사용
 * - 도메인 일관성 > 이벤트 책임분리: Member는 항상 동일한 구조(MemberDto)로 표현
 * - 신규/갱신 판단은 수신 컨텍스트(Cash)의 책임
 */
@Service
@RequiredArgsConstructor
public class CashSyncMemberUseCase {

    private final CashMemberRepository cashMemberRepository;
    private final EventPublisher eventPublisher;

    /**
     * Member를 CashMember로 동기화한다.
     *
     * [동작 방식]
     * - 신규 멤버: CashMember 생성 후 CashMemberCreatedEvent 발행 (Wallet 생성 트리거)
     * - 기존 멤버: CashMember 정보 갱신 (이벤트 발행 없음)
     *
     * @param member MemberDto - 도메인 기반 공유 DTO
     * @return 동기화된 CashMember
     */
    @Transactional
    public CashMember syncMember(MemberDto member) {
        boolean isNew = !cashMemberRepository.existsById(member.getId());

        // 민감정보인 Password는 미러링에 넘기지 않는다
        CashMember cashMember = cashMemberRepository.save(
                new CashMember(
                        member.getId(),
                        member.getCreatedAt(),
                        member.getUpdatedAt(),
                        member.getUsername(),
                        "",
                        member.getNickname(),
                        member.getActivityScore()
                )
        );

        // 신규 생성시에만 CashMemberCreatedEvent 발행
        // → CashEventListener가 수신하여 Wallet 생성
        if (isNew) {
            eventPublisher.publish(
                    new CashMemberCreatedEvent(
                            cashMember.toDto()
                    )
            );
        }


        return cashMember;
    }

}
