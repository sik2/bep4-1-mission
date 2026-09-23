package com.back.boundedContext.payout.out;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.payout.domain.PayoutCandidateItem;

public interface PayoutCandidateItemRepository extends JpaRepository<PayoutCandidateItem, Integer> {
    List<PayoutCandidateItem> findByPayoutItemIsNullAndPaymentDateBeforeOrderByPayeeAscIdAsc(LocalDateTime daysAgo, PageRequest pageRequest);
}