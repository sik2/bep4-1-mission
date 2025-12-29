package com.back.boundedContext.market.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	Optional<Cart> findByBuyer(MarketMember buyer);
}
