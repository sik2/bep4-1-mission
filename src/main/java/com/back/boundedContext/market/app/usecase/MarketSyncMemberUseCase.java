package com.back.boundedContext.market.app.usecase;

import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.out.repository.MarketMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.market.event.MarketMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member를 Market 컨텍스트의 MarketMember로 동기화하는 UseCase
 *
 * [설계 원칙]
 * - MemberJoinedEvent, MemberUpdatedEvent 모두 동일한 MemberDto를 사용
 * - 도메인 일관성 > 이벤트 책임분리: Member는 항상 동일한 구조(MemberDto)로 표현
 * - 신규/갱신 판단은 수신 컨텍스트(Market)의 책임
 */
@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {

    private final MarketMemberRepository marketMemberRepository;
    private final EventPublisher eventPublisher;

    /**
     * Member를 MarketMember로 동기화한다.
     *
     * [동작 방식]
     * - 신규 멤버: MarketMember 생성 후 MarketMemberCreatedEvent 발행 (Cart 생성 트리거)
     * - 기존 멤버: MarketMember 정보 갱신 (이벤트 발행 없음)
     *
     * @param member MemberDto - 도메인 기반 공유 DTO
     * @return 동기화된 MarketMember
     */
    @Transactional
    public MarketMember syncMember(MemberDto member) {
        boolean isNew = !marketMemberRepository.existsById(member.getId());

        // 민감정보인 Password는 미러링에 넘기지 않는다
        MarketMember marketMember = marketMemberRepository.save(
                new MarketMember(
                        member.getId(),
                        member.getCreatedAt(),
                        member.getUpdatedAt(),
                        member.getUsername(),
                        "",
                        member.getNickname(),
                        member.getActivityScore()
                )
        );

        // 신규 생성시에만 MarketMemberCreatedEvent 발행
        // → MarketEventListener가 수신하여 Cart 생성
        if (isNew) {
            eventPublisher.publish(
                    new MarketMemberCreatedEvent(
                            marketMember.toDto()
                    )
            );
        }

        return marketMember;
    }
}

