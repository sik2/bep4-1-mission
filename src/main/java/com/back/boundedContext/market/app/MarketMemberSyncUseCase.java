package com.back.boundedContext.market.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.out.MarketMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.market.event.MarketMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MarketMemberSyncUseCase {
    private final MarketMemberRepository marketMemberRepository;
    private final EventPublisher eventPublisher;

    public MarketMember syncMember(MemberDto memberDto) {
        boolean isNew = !marketMemberRepository.existsById(memberDto.getId());
        MarketMember marketMember = new MarketMember(memberDto.getId(),
                         memberDto.getUsername(),
                         memberDto.getNickname(),
                         "",
                         memberDto.getActivityScore(),
                         memberDto.getCreateDate(),
                         memberDto.getModifyDate());

        if (isNew) {
            eventPublisher.publish(new MarketMemberCreatedEvent(marketMember.toDto()));
        }

        return marketMemberRepository.save(marketMember);
    }
}
