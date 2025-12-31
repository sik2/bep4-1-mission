package com.back.boundedContext.payout.in;

import static org.springframework.transaction.annotation.Propagation.*;
import static org.springframework.transaction.event.TransactionPhase.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.back.boundedContext.payout.app.PayoutFacade;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
import com.back.shared.payout.event.PayoutCompletedEvent;
import com.back.shared.payout.event.PayoutMemberCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PayoutEventListener {
	private final PayoutFacade payoutFacade;

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberJoinedEvent event) {
		payoutFacade.syncMember(event.getMemberDto());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MemberModifiedEvent event) {
		payoutFacade.syncMember(event.getMemberDto());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(PayoutMemberCreatedEvent event) {
		payoutFacade.createPayout(event.getPayoutMemberDto().getId());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(MarketOrderPaymentCompletedEvent event) {
		payoutFacade.addPayoutCandidateItem(event.getOrderDto());
	}

	@TransactionalEventListener(phase = AFTER_COMMIT)
	@Transactional(propagation = REQUIRES_NEW)
	public void handle(PayoutCompletedEvent event) {
		payoutFacade.createPayout(event.getPayoutDto().getPayeeId());
	}
}
