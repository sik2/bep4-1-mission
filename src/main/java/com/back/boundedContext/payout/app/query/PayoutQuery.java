package com.back.boundedContext.payout.app.query;

import com.back.boundedContext.payout.domain.PayoutCandidateItem;
import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.out.repository.PayoutCandidateItemRepository;
import com.back.boundedContext.payout.out.repository.PayoutMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 일단 빠른 진행을 위해 예제대로 묶긴 했는데
 * 기존 코드베이스대로 리팩토링할지 고민
 */
@Service
@RequiredArgsConstructor
public class PayoutQuery {

    private final PayoutMemberRepository payoutMemberRepository;
    private final PayoutCandidateItemRepository payoutCandidateItemRepository;

    public Optional<PayoutMember> findHolingMember() {
        return payoutMemberRepository.findByUsername("holding");
    }

    public Optional<PayoutMember> findMemberById(Long id) {
        return payoutMemberRepository.findById(id);
    }

    public Optional<PayoutMember> findSystemMember() {
        return payoutMemberRepository.findByUsername("system");
    }

    public List<PayoutCandidateItem> findPayoutCandidateItems() {
        return payoutCandidateItemRepository.findAll();
    }
}

