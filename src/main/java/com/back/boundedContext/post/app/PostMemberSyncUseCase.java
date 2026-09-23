package com.back.boundedContext.post.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import com.back.shared.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostMemberSyncUseCase {
    private final PostMemberRepository postMemberRepository;

    public void syncMember(MemberDto memberDto) {
        PostMember postMember = new PostMember(
                memberDto.getId(),
                memberDto.getNickname(),
                memberDto.getUsername(),
                "",
                memberDto.getActivityScore(),
                memberDto.getCreateDate(),
                memberDto.getModifyDate()
        );

        postMemberRepository.save(postMember);
    }
}
