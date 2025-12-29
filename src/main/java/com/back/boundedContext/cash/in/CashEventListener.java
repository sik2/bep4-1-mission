package com.back.boundedContext.cash.in;

import static org.springframework.transaction.annotation.Propagation.*;
import static org.springframework.transaction.event.TransactionPhase.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedContext.cash.app.CashFacade;
import com.back.shared.cash.event.CashMemberCreateEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CashEventListener {
	private final CashFacade cashFacade;

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberJoinedEvent event) {
		cashFacade.syncMember(event.getMemberDto());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberModifiedEvent event) {
		cashFacade.syncMember(event.getMemberDto());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(CashMemberCreateEvent event) {
		cashFacade.createWallet(event.getCashMemberDto());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MarketOrderPaymentRequestedEvent event) {
		cashFacade.completeOrderPayment(event.getOrderDto(), event.getPgPaymentAmount());
	}
}
