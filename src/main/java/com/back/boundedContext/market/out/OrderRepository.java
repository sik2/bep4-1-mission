package com.back.boundedContext.market.out;

import com.back.boundedContext.market.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findOrderById(int id);
}
