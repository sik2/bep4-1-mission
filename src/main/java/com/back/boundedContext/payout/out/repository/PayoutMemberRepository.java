package com.back.boundedContext.payout.out.repository;

import com.back.boundedContext.payout.domain.PayoutMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayoutMemberRepository extends JpaRepository<PayoutMember, Long> {
    Optional<PayoutMember> findByUsername(String username);
}