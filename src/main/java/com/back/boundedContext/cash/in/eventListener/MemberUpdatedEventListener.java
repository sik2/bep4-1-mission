package com.back.boundedContext.cash.in.eventListener;

import com.back.boundedContext.cash.app.facade.CashFacade;
import com.back.shared.member.event.MemberUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * MemberUpdatedEvent를 수신하여 Cash 컨텍스트의 CashMember를 갱신하는 리스너
 *
 * [설계 원칙]
 * - event.getMember()는 MemberDto를 반환 (도메인 기반 공유 DTO)
 * - MemberJoinedEvent와 동일한 syncMember() 호출
 * - 신규/갱신 판단은 CashSyncMemberUseCase에서 처리 (기존 멤버이므로 갱신 수행)
 */
@Component("cashMemberUpdatedEventListener")
@RequiredArgsConstructor
public class MemberUpdatedEventListener {
    private final CashFacade cashFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberUpdatedEvent event) {
        cashFacade.syncMember(event.getMember());
    }
}
