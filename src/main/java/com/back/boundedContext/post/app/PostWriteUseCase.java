package com.back.boundedContext.post.app;

import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.domain.MemberScoreEvent;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.enums.ActivityType;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.RsData.RsData;
import com.back.global.eventPublisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriteUseCase {

    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;
    private final MemberFacade memberFacade;

    public RsData<Post> write(Member author, String title, String content) {
        Post post = postRepository.save(new Post(author, title, content));

        eventPublisher.publish(new MemberScoreEvent(author.getId(), ActivityType.POST));

        return new RsData<>("201-1",
                "%d번 글이 생성되었습니다. 보안 팁 : %s"
                        .formatted(
                                post.getId(),
                                memberFacade.getRandomSecureTip())
                , post
        );
    }
}
