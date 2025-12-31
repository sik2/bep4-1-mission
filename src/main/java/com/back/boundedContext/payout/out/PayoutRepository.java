package com.back.boundedContext.payout.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.payout.domain.Payout;
import com.back.boundedContext.payout.domain.PayoutMember;

public interface PayoutRepository extends JpaRepository<Payout, Integer> {
	Optional<Payout> findByPayeeAndPayoutDateIsNull(PayoutMember payee);

	List<Payout> findByPayoutDateIsNullAndAmountGreaterThanOrderByIdAsc(long amount, Pageable pageable);
}
