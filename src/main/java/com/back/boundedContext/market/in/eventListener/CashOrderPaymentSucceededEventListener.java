package com.back.boundedContext.market.in.eventListener;

import com.back.boundedContext.market.app.facade.MarketFacade;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class CashOrderPaymentSucceededEventListener {
    private final MarketFacade marketFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(CashOrderPaymentSucceededEvent event) {
        Long orderId = event.getOrder().getId();
        marketFacade.completeOrderPayment(orderId);
    }
}