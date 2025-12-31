package com.back.boundedContext.post.app;

import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommandUseCase {
    private final PostMemberRepository postMemberRepository;

    public PostMember savePostMember(PostMember postMember) {
        return postMemberRepository.save(postMember);
    }
}
