package com.back.boundedContext.cash.app.facade;

import com.back.boundedContext.cash.app.query.CashMemberQuery;
import com.back.boundedContext.cash.app.query.WalletQuery;
import com.back.boundedContext.cash.app.usecase.CashCompleteOrderPaymentUseCase;
import com.back.boundedContext.cash.app.usecase.CashCompletePayoutUseCase;
import com.back.boundedContext.cash.app.usecase.CashCreateWalletUseCase;
import com.back.boundedContext.cash.app.usecase.CashSyncMemberUseCase;
import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
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
    private final CashMemberQuery cashMemberQuery;
    private final WalletQuery walletQuery;

    private final CashCreateWalletUseCase cashCreateWalletUseCase;
    private final CashSyncMemberUseCase cashSyncMemberUseCase;
    private final CashCompleteOrderPaymentUseCase cashCompleteOrderPaymentUseCase;
    private final CashCompletePayoutUseCase cashCompletePayoutUseCase;

    @Transactional
    public Wallet createWallet(CashMember holder) {
        return cashCreateWalletUseCase.createWallet(holder);
    }

    @Transactional
    public long count() {return cashMemberQuery.count();}

    @Transactional
    public Optional<CashMember> findCashMemberById(Long id) {
        return cashMemberQuery.findCashMemberById(id);
    }

    @Transactional(readOnly = true)
    public Optional<CashMember> findCashMemberByUsername(String username) {
        return cashMemberQuery.findCashMemberByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Wallet> findWalletByHolder(CashMember holder) {
        return walletQuery.findWalletByHolder(holder);
    }

    @Transactional
    public CashMember syncMember(MemberDto member) {
        return cashSyncMemberUseCase.syncMember(member);
    }

    @Transactional
    public void completeOrderPayment(OrderDto order, long pgPaymentAmount) {
        cashCompleteOrderPaymentUseCase.completeOrderPayment(order, pgPaymentAmount);
    }    @Transactional(readOnly = true)
    public Optional<Wallet> findWalletByHolderId(Long holderId) {
        return walletQuery.findWalletByHolderId(holderId);
    }

    @Transactional
    public void completePayout(PayoutDto payout) {
        cashCompletePayoutUseCase.completePayout(payout);
    }
}
