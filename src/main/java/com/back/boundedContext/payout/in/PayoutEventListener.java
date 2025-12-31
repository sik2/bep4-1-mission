package com.back.boundedContext.payout.in;

import com.back.boundedContext.payout.app.PayoutFacade;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.member.event.MemberJoinEvent;
import com.back.shared.member.event.MemberModifyEvent;
import com.back.shared.payout.event.PayoutCompletedEvent;
import com.back.shared.payout.event.PayoutMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class PayoutEventListener {
    private final PayoutFacade payoutFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberJoinEvent event) {
        payoutFacade.syncMember(event.getMemberDto());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberModifyEvent event) {
        payoutFacade.syncMember(event.getMemberDto());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(PayoutMemberCreatedEvent event) {
        payoutFacade.createPayout(event.payoutMemberDto().id());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MarketOrderPaymentCompletedEvent event) {
        payoutFacade.addPayoutCandidateItems(event.orderDto());
    }

    // 정산받을 Payout 정보를 신규 생성
    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(PayoutCompletedEvent event) {
        System.out.println("payoutCompletedEvent");
        payoutFacade.createPayout(event.getPayout().payeeId());
    }
}
