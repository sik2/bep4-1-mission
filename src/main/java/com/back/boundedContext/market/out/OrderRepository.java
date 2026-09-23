package com.back.boundedContext.market.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.market.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
