package com.back.boundedContext.payout.app.usecase;

import com.back.boundedContext.payout.domain.Payout;
import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.out.repository.PayoutMemberRepository;
import com.back.boundedContext.payout.out.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutCreatePayoutUseCase {
    private final PayoutRepository payoutRepository;
    private final PayoutMemberRepository payoutMemberRepository;

    public Payout createPayout(Long payeeId) {

        PayoutMember _payee = payoutMemberRepository.getReferenceById(payeeId);
        Payout payout = payoutRepository.save(
                new Payout(
                        _payee
                )
        );

        return payout;

    }
}