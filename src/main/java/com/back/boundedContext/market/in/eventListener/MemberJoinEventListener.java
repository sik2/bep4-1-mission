package com.back.boundedContext.market.in.eventListener;

import com.back.boundedContext.market.app.facade.MarketFacade;
import com.back.shared.member.event.MemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * MemberJoinedEvent를 수신하여 Market 컨텍스트에 Member를 동기화하는 리스너
 *
 * [설계 원칙]
 * - event.getMember()는 MemberDto를 반환 (도메인 기반 공유 DTO)
 * - MemberJoinedEvent, MemberUpdatedEvent 모두 동일한 MemberDto 구조 사용
 * - 신규/갱신 판단은 MarketSyncMemberUseCase에서 처리
 *
 * [이벤트 흐름]
 * MemberJoinedEvent → MarketFacade.syncMember()
 *   → MarketMember 생성 + MarketMemberCreatedEvent 발행
 *   → MarketMemberCreatedEventListener가 수신하여 Cart 생성
 */
@Component("marketMemberJoinEventListener")
@RequiredArgsConstructor
public class MemberJoinEventListener {

    private final MarketFacade marketFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberJoinedEvent event) {
        marketFacade.syncMember(event.getMember());
    }
}
