package com.back.boundedContext.cash.out;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;

public interface WalletRepository extends CrudRepository<Wallet, Integer> {
	Optional<Wallet> findByHolder(CashMember holder);
	Optional<Wallet> findByHolderId(int holderId);
}
