package com.back.boundedContext.cash.app;

import org.springframework.stereotype.Service;

import com.back.boundedContext.cash.domain.CashLog.EventType;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.shared.payout.dto.PayoutDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashCompletePayoutUseCase {
    private final CashSupport cashSupport;

    public void completePayout(PayoutDto payout) {
        Wallet holdingWallet = cashSupport.findHoldingWallet().get();
        Wallet payeeWallet = cashSupport.findWalletByHolderId(payout.payeeId()).get();

        holdingWallet.debit(
                payout.amount(),
                payout.isPayeeSystem() ?
                EventType.정산지급__상품판매_수수료 :
                EventType.정산지급__상품판매_대금,
                payout.getModelTypeCode(),
                payout.id()
        );

        payeeWallet.credit(
                payout.amount(),
                payout.isPayeeSystem() ?
                EventType.정산수령__상품판매_수수료 :
                EventType.정산수령__상품판매_대금,
                payout.getModelTypeCode(),
                payout.id()
        );
    }
}
