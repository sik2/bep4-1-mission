package com.back.boundedContext.market.in;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedContext.market.app.MarketFacade;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.event.MarketMemberCreatedEvent;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MarketEventListener {
	private final MarketFacade marketFacade;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MemberJoinedEvent event) {
		marketFacade.syncMember(event.getMemberDto());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MemberModifiedEvent event) {
		marketFacade.syncMember(event.getMemberDto());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(MarketMemberCreatedEvent event) {
		marketFacade.createCart(event.getMarketMemberDto());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(CashOrderPaymentSucceededEvent event) {
		marketFacade.completeOrderPayment(event.getOrderDto().getId());
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void  handle(CashOrderPaymentFailedEvent event) {
		marketFacade.cancelOrderRequestPayment(event.getOrderDto().getId());
	}
}
