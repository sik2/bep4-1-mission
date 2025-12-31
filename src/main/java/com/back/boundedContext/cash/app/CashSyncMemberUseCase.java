package com.back.boundedContext.cash.app;

import org.springframework.stereotype.Service;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.cash.event.CashMemberCreateEvent;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashSyncMemberUseCase {
	private final CashMemberRepository cashMemberRepository;
	private final EventPublisher eventPublisher;

	public CashMember syncMember(MemberDto memberDto) {

		boolean isNew = !cashMemberRepository.existsById(memberDto.getId());

		CashMember _member = cashMemberRepository.save(
			new CashMember(
				memberDto.getId(),
				memberDto.getCreateTime(),
				memberDto.getModifyTime(),
				memberDto.getUserName(),
				"",
				memberDto.getNickName(),
				memberDto.getActivityScore()
			)
		);

		if (isNew) {
			eventPublisher.publish(
				new CashMemberCreateEvent(
					_member.toDto()
				)
			);
		}

		return _member;
	}
}
