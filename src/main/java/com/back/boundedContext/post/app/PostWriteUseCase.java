package com.back.boundedContext.post.app;

import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCreatedEvent;
import com.back.global.response.RsData;
import com.back.shared.member.out.MemberApiClient;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostWriteUseCase {
    private final PostRepository postRepository;
    private final MemberApiClient memberApiClient;
    private final EventPublisher eventPublisher;

    @Transactional
    public RsData<Post> write(PostMember author, String title, String content) {
        Post post = postRepository.save(new Post(author, title, content));

        // 게시글 작성시 활동점수 3점 추가
        eventPublisher.publish(new PostCreatedEvent(post.toDto()));

        String randomSecureTip = memberApiClient.getRandomSecureTip();

        return new RsData<>(
                "201-1",
                "%d번 글이 생성되었습니다. 보안 팁 : %s"
                        .formatted(post.getId(), randomSecureTip),
                post
        );
    }
}
