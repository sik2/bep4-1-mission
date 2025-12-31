package com.back.boundedContext.cash.in.eventListener;

import com.back.boundedContext.cash.app.facade.CashFacade;
import com.back.boundedContext.cash.domain.CashMember;
import com.back.shared.cash.event.CashMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component("cashMemberCreatedEventListener")
@RequiredArgsConstructor
public class CashMemberCreatedEventListener {

    private final CashFacade cashFacade;

    /**
     * CashMemberCreatedEvent가 발생했을 때 Wallet을 생성한다.
     * CashMember 생성 후 커밋이 완료되면 새로운 트랜잭션에서 Wallet을 생성한다.
     * @param event
     */
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(CashMemberCreatedEvent event) {
        // 이벤트 페이로드의 ID로 CashMember 조회
        CashMember member = cashFacade.findCashMemberById(event.getMember().getId()).get();
        // 조회된 CashMember로 Wallet 생성
        cashFacade.createWallet(member);

    }

}
