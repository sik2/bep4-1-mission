package com.back.boundedContext.cash.in.eventListener;

import com.back.boundedContext.cash.app.facade.CashFacade;
import com.back.shared.member.event.MemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * MemberJoinedEvent를 수신하여 Cash 컨텍스트에 Member를 동기화하는 리스너
 *
 * [설계 원칙]
 * - event.getMember()는 MemberDto를 반환 (도메인 기반 공유 DTO)
 * - MemberJoinedEvent, MemberUpdatedEvent 모두 동일한 MemberDto 구조 사용
 * - 신규/갱신 판단은 CashSyncMemberUseCase에서 처리
 *
 * [이벤트 흐름]
 * MemberJoinedEvent → CashFacade.syncMember()
 *   → CashMember 생성 + CashMemberCreatedEvent 발행
 *   → CashMemberCreatedEventListener가 수신하여 Wallet 생성
 */
@Component("cashMemberJoinEventListener")
@RequiredArgsConstructor
public class MemberJoinEventListener {
    private final CashFacade cashFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberJoinedEvent event) {
        cashFacade.syncMember(event.getMember());
    }

}

