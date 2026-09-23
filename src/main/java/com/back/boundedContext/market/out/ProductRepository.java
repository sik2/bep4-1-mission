package com.back.boundedContext.market.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.market.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
