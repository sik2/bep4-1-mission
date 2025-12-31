package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.market.app.CashCompleteOrderPaymentUseCase;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.dto.PayoutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashFacade {
    private final CashSupport cashSupport;
    private final CashSyncMemberUseCase cashSyncMemberUseCase;
    private final CashCreateWalletUseCase cashCreateWalletUseCase;
    private final CashCompleteOrderPaymentUseCase cashCompleteOrderPaymentUseCase;
    private final CashCompletePayoutUseCase cashCompletePayoutUseCase;

    @Transactional
    public CashMember syncMember(MemberDto member) {
        return cashSyncMemberUseCase.syncMember(member);
    }

    @Transactional
    public Wallet createWallet(CashMemberDto member) {
        return cashCreateWalletUseCase.createWallet(member);
    }

    @Transactional
    public void completeOrderPayment(OrderDto order, long pgPaymentAmount) {
        cashCompleteOrderPaymentUseCase.completeOrderPayment(order, pgPaymentAmount);
    }

    public Optional<CashMember> findMemberByUsername(String username) {
        return cashSupport.findByUsername(username);
    }

    public Optional<Wallet> findWalletByMember(CashMember member) {
        return cashSupport.findByUsername(member);
    }


    @Transactional(readOnly = true)
    public Optional<Wallet> findWalletByMemberId(int memberId) {
        return cashSupport.findWalletByMemberId(memberId);
    }

    @Transactional
    public void completePayout(PayoutDto payout) {
        cashCompletePayoutUseCase.completePayout(payout);
    }

}
