package com.back.boundedContext.market.app;

import com.back.global.eventPublisher.EventPublisher;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.out.MarketMemberRepository;
import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.market.event.MarketMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {
    private final MarketMemberRepository marketMemberRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public MarketMember syncMember(MemberDto memberDto) {
        boolean isNew = !marketMemberRepository.existsById(memberDto.getId());

        MarketMember member = marketMemberRepository.save(
                new MarketMember(
                        memberDto.getId(),
                        memberDto.getUsername(),
                        "",
                        memberDto.getNickname(),
                        memberDto.getCreateDate(),
                        memberDto.getModifyDate(),
                        memberDto.getActivityScore()
                )
        );

        if(isNew) {
            eventPublisher.publish(new MarketMemberCreatedEvent(member.toDto()));
        }

        return member;
    }
}
