package com.back.boundedContext.market.out;

import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
