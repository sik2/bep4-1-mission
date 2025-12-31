package com.back.boundedContext.payout.app;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.back.boundedContext.payout.domain.Payout;
import com.back.boundedContext.payout.domain.PayoutCandidateItem;
import com.back.boundedContext.payout.domain.PayoutItem;
import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.domain.PayoutPolicy;
import com.back.boundedContext.payout.out.PayoutCandidateItemRepository;
import com.back.boundedContext.payout.out.PayoutRepository;
import com.back.global.RsData.RsData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayoutCollectPayoutItemsMoreUseCase {
	private final PayoutRepository payoutRepository;
	private final PayoutCandidateItemRepository payoutCandidateItemRepository;

	public RsData<Integer> collectPayoutItemsMore(int limit) {
		List<PayoutCandidateItem> payoutReadyCandidateItems = findPayoutReadyCandidateItems(limit);

		if(payoutReadyCandidateItems.isEmpty()) {
			return new RsData<>("200-1", "더 이상 정산에 추가할 항목이 없습니다.", 0);
		}

		payoutReadyCandidateItems.stream()
			.collect(Collectors.groupingBy(PayoutCandidateItem::getPayee))
			.forEach((payee, candidateItems) -> {
				Payout payout = findActiveByPayee(payee).get();

				candidateItems.forEach(candidateItem -> {
					PayoutItem payoutItem = payout.addItem(
						candidateItem.getEventType(),
						candidateItem.getRelTypeCode(),
						candidateItem.getRelId(),
						candidateItem.getPaymentTime(),
						candidateItem.getPayer(),
						candidateItem.getPayee(),
						candidateItem.getAmount()
					);

					candidateItem.setPayoutItem(payoutItem);
				});
			});

		return new RsData<>(
			"201-1",
			"%d건의 정산데이터가 생성되었습니다.".formatted(payoutReadyCandidateItems.size()),
			payoutReadyCandidateItems.size()
		);
	}

	private List<PayoutCandidateItem> findPayoutReadyCandidateItems(int limit) {
		LocalDateTime daysAgo = LocalDateTime
			.now()
			.minusDays(PayoutPolicy.PAYOUT_READY_WAITING_DAYS)
			.toLocalDate()
			.atStartOfDay();

		return payoutCandidateItemRepository.findByPayoutItemIsNullAndPaymentTimeBeforeOrderByPayeeAscIdAsc(
			daysAgo,
			PageRequest.of(0, limit)
		);
	}

	private Optional<Payout> findActiveByPayee(PayoutMember payee) {
		return payoutRepository.findByPayeeAndPayoutDateIsNull(payee);
	}
}
