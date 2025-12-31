package com.back.boundedContext.payout.app;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.payout.domain.Payout;
import com.back.boundedContext.payout.domain.PayoutCandidateItem;
import com.back.global.RsData.RsData;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayoutFacade {
	private final PayoutSyncMemberUseCase payoutSyncMemberUseCase;
	private final PayoutAddPayoutCandidateItemUseCase payoutAddPayoutCandidateItemUseCase;
	private final PayoutCreatePayoutUseCase payoutCreatePayoutUseCase;
	private final PayoutCollectPayoutItemsMoreUseCase payoutCollectPayoutItemsMoreUseCase;
	private final PayoutSupport payoutSupport;
	private final PayoutCompletePayoutsMoreUseCase payoutCompletePayoutsMoreUseCase;

	//회원 내역 동기화
	@Transactional
	public void syncMember(MemberDto memberDto) {
		payoutSyncMemberUseCase.syncMember(memberDto);
	}

	//정산 목록을 담을 Payout 생성
	@Transactional
	public Payout createPayout(int payeeId) {
		return payoutCreatePayoutUseCase.createPayout(payeeId);
	}

	//정산 대기 목록에 물품 올리기
	@Transactional
	public void addPayoutCandidateItem(OrderDto orderDto) {
		payoutAddPayoutCandidateItemUseCase.addPayoutCandidateItems(orderDto);
	}

	//정산 집행 전 정산할 내역 불러오기
	@Transactional
	public RsData<Integer> collectPayoutItemsMore(int limit) {
		return payoutCollectPayoutItemsMoreUseCase.collectPayoutItemsMore(limit);
	}

	//정산 대기중인 상품 내역 불러오기
	@Transactional(readOnly = true)
	public List<PayoutCandidateItem> findPayoutCandidateItems() {
		return payoutSupport
			.findPayoutCandidateItems();
	}

	//정산 집행
	@Transactional
	public RsData<Integer> completePayoutsMore(int limit) {
		return payoutCompletePayoutsMoreUseCase.completePayoutsMore(limit);
	}

}
