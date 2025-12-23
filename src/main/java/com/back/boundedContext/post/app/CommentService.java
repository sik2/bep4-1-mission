package com.back.boundedContext.post.app;

import com.back.boundedContext.member.domain.MemberScoreEvent;
import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.domain.enums.ActivityType;
import com.back.global.eventPublisher.EventPublisher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final EventPublisher eventPublisher;

    @Transactional
    public void addComment(Post post, Member author, String content) {
        post.addComment(author, content);

        eventPublisher.publish(
                new MemberScoreEvent(author.getId(), ActivityType.COMMENT)
        );
    }
}
