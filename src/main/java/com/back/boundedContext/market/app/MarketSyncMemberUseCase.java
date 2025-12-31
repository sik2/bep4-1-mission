package com.back.boundedContext.market.app;

import org.springframework.stereotype.Service;

import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.out.MarketMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.market.event.MarketMemberCreatedEvent;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketSyncMemberUseCase {
	private final MarketMemberRepository marketMemberRepository;
	private final EventPublisher eventPublisher;

	public MarketMember syncMember(MemberDto memberDto) {
		boolean isNew = !marketMemberRepository.existsById(memberDto.getId());

		MarketMember _member = marketMemberRepository.save(
			new MarketMember(
				memberDto.getId(),
				memberDto.getCreateTime(),
				memberDto.getModifyTime(),
				memberDto.getUserName(),
				"",
				memberDto.getNickName(),
				memberDto.getActivityScore())
		);

		if (isNew) {
			eventPublisher.publish(
				new MarketMemberCreatedEvent(
					_member.toDto()
				)
			);
		}

		return _member;
	}
}
