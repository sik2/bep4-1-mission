package com.back.boundedContext.cash.app.query;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.CashPolicy;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.cash.out.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class WalletQuery {

    private final WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public Optional<Wallet> findWalletByHolder(CashMember holder) {
        return walletRepository.findByHolder(holder);
    }

    public Optional<Wallet> findWalletByHolderId(Long holderId) {
        return walletRepository.findByHolderId(holderId);
    }

    public Optional<Wallet> findHoldingWallet() {
        return walletRepository.findByHolderId(CashPolicy.HOLDING_MEMBER_ID);
    }
}
