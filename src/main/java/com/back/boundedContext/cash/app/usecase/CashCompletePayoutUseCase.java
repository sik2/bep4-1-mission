package com.back.boundedContext.cash.app.usecase;

import com.back.boundedContext.cash.app.query.WalletQuery;
import com.back.boundedContext.cash.domain.CashEventType;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.shared.payout.dto.PayoutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCompletePayoutUseCase {
    private final WalletQuery walletQuery;

    public void completePayout(PayoutDto payout) {
        Wallet holdingWallet = walletQuery.findHoldingWallet().get();
        Wallet payeeWallet = walletQuery.findWalletByHolderId(payout.getPayeeId()).get();

        holdingWallet.debit(
                payout.getAmount(),
                payout.isPayeeSystem() ? CashEventType.정산지급__상품판매_수수료 : CashEventType.정산지급__상품판매_대금,
                payout.getModelTypeCode(),
                payout.getId()
        );

        payeeWallet.credit(
                payout.getAmount(),
                payout.isPayeeSystem() ? CashEventType.정산수령__상품판매_수수료 : CashEventType.정산수령__상품판매_대금,
                payout.getModelTypeCode(),
                payout.getId()
        );
    }
}