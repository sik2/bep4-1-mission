package com.back.boundedContext.payout.app;

import org.springframework.stereotype.Service;

import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.out.PayoutMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.event.PayoutMemberCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayoutSyncMemberUseCase {
	private final PayoutMemberRepository payoutMemberRepository;
	private final EventPublisher eventPublisher;

	public PayoutMember syncMember(MemberDto memberDto) {
		boolean isNew = !payoutMemberRepository.existsById(memberDto.getId());

		PayoutMember _member = payoutMemberRepository.save(
			new PayoutMember(
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
				new PayoutMemberCreatedEvent(_member.toDto())
			);
		}

		return _member;
	}
}
