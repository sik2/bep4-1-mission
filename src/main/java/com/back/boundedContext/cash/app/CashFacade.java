package com.back.boundedContext.cash.app;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.dto.PayoutDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashFacade {
	private final CashSupport cashSupport;
	private final CashSyncMemberUseCase cashSyncMemberUseCase;
	private final CashCreateWalletUseCase cashCreateWalletUseCase;
	private final CashCompleteOrderPaymentUseCase cashCompleteOrderPaymentUseCase;
	private final CashCompletePayoutUseCase cashCompletePayoutUseCase;

	@Transactional
	public CashMember syncMember(MemberDto memberDto) {

		return cashSyncMemberUseCase.syncMember(memberDto);
	}

	@Transactional
	public Wallet createWallet(CashMemberDto holder) {

		return cashCreateWalletUseCase.createWallet(holder);
	}

	@Transactional(readOnly = true)
	public Optional<CashMember> findMemberByUserName(String userName) {

		return cashSupport.findMemberByUserName(userName);
	}

	@Transactional(readOnly = true)
	public Optional<Wallet> findWalletByHolder(CashMember holder) {

		return cashSupport.findWalletByHolder(holder);
	}

	@Transactional
	public void completeOrderPayment(OrderDto orderDto, long pgPaymentAmount) {
		cashCompleteOrderPaymentUseCase.completeOrderPayment(orderDto, pgPaymentAmount);
	}

	@Transactional(readOnly = true)
	public Optional<Wallet> findWalletByHolderId(int id) {
		return cashSupport.findWalletByHolderId(id);
	}

	//정산 집행 후 시스템,회원 지갑에 정산 내역 반영
	@Transactional
	public void completePayout(PayoutDto payout) {
		cashCompletePayoutUseCase.completePayout(payout);
	}
}
