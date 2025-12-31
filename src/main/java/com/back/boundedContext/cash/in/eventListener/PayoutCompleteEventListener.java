package com.back.boundedContext.cash.in.eventListener;

import com.back.boundedContext.cash.app.facade.CashFacade;
import com.back.shared.payout.event.PayoutCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
@RequiredArgsConstructor
public class PayoutCompleteEventListener {

    private final CashFacade cashFacade;

    @TransactionalEventListener
    @Transactional(propagation = REQUIRES_NEW)
    public void handle(PayoutCompletedEvent event) {
        cashFacade.completePayout(event.getPayout());
    }
}
