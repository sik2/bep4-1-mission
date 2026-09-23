package com.back.boundedContext.cash.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.cash.event.CashMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CashSyncMemberUseCase {
    private final CashMemberRepository cashMemberRepository;
    private final CashSupport cashSupport;
    private final EventPublisher eventPublisher;

    public CashMember syncMember(MemberDto memberDto) {
        boolean isNew = cashSupport.isNew(memberDto.getId());

        CashMember cashMember = new CashMember(memberDto.getId(), memberDto.getNickname(),
                                               memberDto.getUsername(), "",
                                               memberDto.getActivityScore(), memberDto.getCreateDate(),
                                               memberDto.getModifyDate());

        cashMember = cashMemberRepository.save(cashMember);

        if (isNew) {
            eventPublisher.publish(new CashMemberCreatedEvent(cashMember.toDto()));
        }

        return cashMember;
    }
}
