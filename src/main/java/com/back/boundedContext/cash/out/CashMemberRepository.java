package com.back.boundedContext.cash.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.cash.domain.CashMember;

public interface CashMemberRepository extends JpaRepository<CashMember, Integer> {
	Optional<CashMember> findByUserName(String username);
}
