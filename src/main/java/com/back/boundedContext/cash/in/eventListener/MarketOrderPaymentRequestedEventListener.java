package com.back.boundedContext.cash.in.eventListener;

import com.back.boundedContext.cash.app.facade.CashFacade;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class MarketOrderPaymentRequestedEventListener {

    private final CashFacade cashFacade;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(MarketOrderPaymentRequestedEvent event) {
        cashFacade.completeOrderPayment(event.getOrder(), event.getPgPaymentAmount());
    }
}
