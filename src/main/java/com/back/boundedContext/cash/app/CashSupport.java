package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.boundedContext.cash.out.WalletRepository;
import com.back.boundedContext.market.domain.policy.CashPolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CashSupport {
    private final CashMemberRepository cashMemberRepository;
    private final WalletRepository walletRepository;

    public Optional<CashMember> findByUsername(String username) {
        return cashMemberRepository.findByUsername(username);
    }

    public Optional<Wallet> findByUsername(CashMember cashMember) {
        return walletRepository.findWalletByMember(cashMember);
    }

    public Optional<Wallet> findWalletByMemberId(int holderId) {
        return walletRepository.findByMemberId(holderId);
    }

    public Optional<Wallet> findHoldingWallet() {
        return walletRepository.findByMemberId(CashPolicy.HOLDING_MEMBER_ID);
    }
}
