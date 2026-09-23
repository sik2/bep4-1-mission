package com.back.boundedContext.payout.app;

import org.springframework.stereotype.Service;

import com.back.boundedContext.payout.domain.Payout;
import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.out.PayoutMemberRepository;
import com.back.boundedContext.payout.out.PayoutRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutCreatePayoutUseCase {
    private final PayoutRepository payoutRepository;
    private final PayoutMemberRepository payoutMemberRepository;

    public Payout createPayOut(int  payeeId) {
        PayoutMember payee = payoutMemberRepository.getReferenceById(payeeId);

        Payout payout = payoutRepository.save(new Payout(payee));

        return payout;
    }
}
