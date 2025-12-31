package com.back.boundedContext.market.in.eventListener;

import com.back.boundedContext.market.app.facade.MarketFacade;
import com.back.shared.member.event.MemberUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

 /**
 * MemberUpdatedEvent를 수신하여 Market 컨텍스트의 MarketMember를 갱신하는 리스너
 *
 * [설계 원칙]
 * - event.getMember()는 MemberDto를 반환 (도메인 기반 공유 DTO)
 * - MemberJoinedEvent와 동일한 syncMember() 호출
 * - 신규/갱신 판단은 MarketSyncMemberUseCase에서 처리 (기존 멤버이므로 갱신 수행)
 */
@Component("marketMemberUpdateEventListener")
@RequiredArgsConstructor
public class MemberUpdateEventListener {

    private final MarketFacade marketFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberUpdatedEvent event) {
        marketFacade.syncMember(event.getMember());
    }
}

