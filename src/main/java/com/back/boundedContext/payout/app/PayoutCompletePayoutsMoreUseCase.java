package com.back.boundedContext.payout.app;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.back.boundedContext.payout.domain.Payout;
import com.back.boundedContext.payout.out.PayoutRepository;
import com.back.global.RsData.RsData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayoutCompletePayoutsMoreUseCase {
	private final PayoutRepository payoutRepository;

	public RsData<Integer> completePayoutsMore(int limit) {

		//정산 진행할 Payout 내역 불러오기
		List<Payout> activePayouts = findActivePayouts(limit);

		if (activePayouts.isEmpty())
			return new RsData<>("200-1", "더 이상 정산할 정산내역이 없습니다.", 0);

		// 정산완료 처리
		activePayouts.forEach(Payout::completePayout);

		return new RsData<>(
			"201-1",
			"%d건의 정산이 처리되었습니다.".formatted(activePayouts.size()),
			activePayouts.size()
		);
	}

	private List<Payout> findActivePayouts(int limit) {
		return payoutRepository.findByPayoutDateIsNullAndAmountGreaterThanOrderByIdAsc(0, PageRequest.of(0, limit));
	}
}
