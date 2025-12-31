package com.back.boundedContext.market.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.market.domain.MarketMember;

public interface MarketMemberRepository extends JpaRepository<MarketMember, Integer> {
	Optional<MarketMember> findByUserName(String userName);
}
