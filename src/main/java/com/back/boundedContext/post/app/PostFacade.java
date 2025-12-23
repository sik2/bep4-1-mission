package com.back.boundedContext.post.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.domain.MemberScoreEvent;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.enums.ActivityType;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.RsData.RsData;
import com.back.global.eventPublisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostRepository postRepository;
    private final PostWriteUseCase postWriteUseCase;

    public long count() {
        return postRepository.count();
    }

    @Transactional
    public RsData<Post> write(Member author, String title, String content) {
        return postWriteUseCase.write(author, title, content);
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }
}
