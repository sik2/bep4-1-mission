package com.back.boundedContext.post.app;

import org.springframework.stereotype.Service;

import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostSyncMemberUseCase {
	private final PostMemberRepository postMemberRepository;

	public PostMember syncMember(MemberDto memberDto) {
		PostMember postMember = new PostMember(
			memberDto.getId(),
			memberDto.getCreateTime(),
			memberDto.getModifyTime(),
			memberDto.getUserName(),
			"",
			memberDto.getNickName(),
			memberDto.getActivityScore()
		);

		return postMemberRepository.save(postMember);
	}
}
