package com.back.boundedContext.payout.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.payout.domain.PayoutMember;

public interface PayoutMemberRepository extends JpaRepository<PayoutMember, Integer> {
	Optional<PayoutMember> findByUserName(String userName);
}
