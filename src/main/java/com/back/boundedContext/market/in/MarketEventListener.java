package com.back.boundedContext.market.in;

import com.back.boundedContext.cash.app.CashFacade;
import com.back.boundedContext.market.app.MarketFacade;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.event.MarketMemberCreatedEvent;
import com.back.shared.member.event.MemberJoinEvent;
import com.back.shared.member.event.MemberModifyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class MarketEventListener {
    private final MarketFacade marketFacade;
    private final CashFacade cashFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberJoinEvent event) {
        marketFacade.syncMember(event.getMemberDto());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MemberModifyEvent event) {
        marketFacade.syncMember(event.getMemberDto());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MarketMemberCreatedEvent event) {
        marketFacade.createCart(event.getMarketMemberDto());
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(CashOrderPaymentSucceededEvent event) {
        int orderId = event.order().id();
        marketFacade.completeOrderPayment(orderId);
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(CashOrderPaymentFailedEvent event) {
        int orderId = event.order().id();
        marketFacade.cancelOrderRequestPayment(orderId);
    }
}
