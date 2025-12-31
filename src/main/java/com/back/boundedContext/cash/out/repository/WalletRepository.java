package com.back.boundedContext.cash.out.repository;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByHolder(CashMember holder);

    Optional<Wallet> findByHolderId(Long holderId);
}
