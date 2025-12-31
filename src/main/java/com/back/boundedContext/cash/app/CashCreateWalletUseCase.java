package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.boundedContext.cash.out.WalletRepository;
import com.back.shared.cash.dto.CashMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashCreateWalletUseCase {
    private final CashMemberRepository cashMemberRepository;
    private final WalletRepository walletRepository;

    public Wallet createWallet(CashMemberDto memberDto) {
        CashMember member = cashMemberRepository.findById(memberDto.id()).get();
        Wallet wallet = new Wallet(member);

        return walletRepository.save(wallet);
    }
}
