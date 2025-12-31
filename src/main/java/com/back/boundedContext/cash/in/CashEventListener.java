package com.back.boundedContext.cash.in;

import com.back.boundedContext.cash.app.CashFacade;
import com.back.shared.cash.event.CashMemberCreateEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import com.back.shared.member.event.MemberJoinEvent;
import com.back.shared.member.event.MemberModifyEvent;
import com.back.shared.payout.event.PayoutCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class CashEventListener {
    private final CashFacade cashFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberJoinEvent event) {
        cashFacade.syncMember(event.getMemberDto());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberModifyEvent event) {
        cashFacade.syncMember(event.getMemberDto());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(CashMemberCreateEvent event) {
        cashFacade.createWallet(event.getMemberDto());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MarketOrderPaymentRequestedEvent event) {
        cashFacade.completeOrderPayment(event.getOrderDto(), event.getPgPaymentAmount());
    }

    @TransactionalEventListener
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(PayoutCompletedEvent event) {
        cashFacade.completePayout(event.getPayout());
    }
}
