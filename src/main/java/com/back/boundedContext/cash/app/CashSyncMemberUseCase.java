package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.cash.event.CashMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CashSyncMemberUseCase {
    private final CashMemberRepository cashMemberRepository;
    private final EventPublisher eventPublisher;

    // todo : 동시성 문제 해결
    public CashMember syncMember(MemberDto memberDto) {
        boolean isNew = !cashMemberRepository.existsById(memberDto.memberId());

        CashMember _member = cashMemberRepository.save(CashMember.from(memberDto));

        if (isNew) {
            eventPublisher.publish(
                    new CashMemberCreatedEvent(
                            new CashMemberDto(_member)
                    )
            );
        }
        return _member;
    }
}
