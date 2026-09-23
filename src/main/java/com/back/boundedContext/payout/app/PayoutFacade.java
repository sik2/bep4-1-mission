package com.back.boundedContext.payout.app;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.payout.domain.Payout;
import com.back.boundedContext.payout.domain.PayoutCandidateItem;
import com.back.global.rsData.RsData;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PayoutFacade {
    private final PaymentSyncMemberUseCase paymentSyncMemberUseCase;
    private final PayoutCreatePayoutUseCase payoutCreatePayoutUseCase;
    private final PayoutAddPayoutCandidateItemUseCase payoutAddPayoutCandidateItemUseCase;
    private final PayoutCollectPayoutItemsMoreUseCase payoutCollectPayoutItemsMoreUseCase;
    private final PayoutSupport payoutSupport;
    private final PayoutCompletePayoutItemsMoreUseCase payoutCompletePayoutItemsMoreUseCase;

    public void syncMember(MemberDto member) {
        paymentSyncMemberUseCase.syncMember(member);
    }

    public Payout createPayout(int  payeeId) {
        return payoutCreatePayoutUseCase.createPayOut(payeeId);
    }

    public void addPayoutCandidateItems(OrderDto order) {
        payoutAddPayoutCandidateItemUseCase.addPayoutCandidateItems(order);
    }

    @Transactional
    public RsData<Integer> collectPayoutItemsMore(int limit) {
        return payoutCollectPayoutItemsMoreUseCase.collectPayoutItemsMore(limit);
    }

    @Transactional(readOnly = true)
    public List<PayoutCandidateItem> findPayoutCandidateItems() {
        return payoutSupport
                .findPayoutCandidateItems();
    }

    public RsData<Integer> completePayoutsMore(int limit) {
        return payoutCompletePayoutItemsMoreUseCase.completePayoutsMore(limit);
    }
}
