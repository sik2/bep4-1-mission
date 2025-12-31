package com.back.boundedContext.payout.in.eventListener;


import com.back.boundedContext.payout.app.facade.PayoutFacade;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberUpdatedEvent;
import com.back.shared.payout.event.PayoutCompletedEvent;
import com.back.shared.payout.event.PayoutMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

/**
 * 일단 예제에서 재공한대로 넣고 코드스타일 일관성을 맞추자면 나중에 쪼개자
 * 원래 스타일대로면 이벤트마다 리스너를 쪼개서 넣는데 진도 먼저 따라가고 생각
 */
@Component
@RequiredArgsConstructor
public class PayoutEventListener {
    private final PayoutFacade payoutFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberJoinedEvent event) {
        payoutFacade.syncMember(event.getMember());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberUpdatedEvent event) {
        payoutFacade.syncMember(event.getMember());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(PayoutMemberCreatedEvent event) {
        payoutFacade.createPayout(event.getMember().getId());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MarketOrderPaymentCompletedEvent event) {
        payoutFacade.addPayoutCandidateItems(event.getOrder());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(PayoutCompletedEvent event) {
        payoutFacade.createPayout(event.getPayout().getPayeeId());
    }



}