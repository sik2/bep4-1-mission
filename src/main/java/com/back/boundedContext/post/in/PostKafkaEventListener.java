package com.back.boundedContext.post.in;

import com.back.boundedContext.post.app.PostFacade;
import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostKafkaEventListener {

    private static final String MEMBER_JOINED_TOPIC = "MemberJoinedEvent";
    private static final String MEMBER_MODIFIED_TOPIC = "MemberModifiedEvent";

    private static final String MEMBER_JOINED_GROUP =
            "PostKafkaEventListener_MemberJoinedEvent";

    private static final String MEMBER_MODIFIED_GROUP =
            "PostKafkaEventListener_MemberModifiedEvent";

    private final PostFacade postFacade;

    @KafkaListener(topics = MEMBER_JOINED_TOPIC, groupId = MEMBER_JOINED_GROUP)
    @Transactional
    public void handle(MemberJoinedEvent event) {
        System.out.println(
                "[Kafka] MemberJoinedEvent 수신 memberId = "
                        + event.getMember().getId()
        );
        if (postFacade.findById(event.getMember().getId()).isPresent()) {
            return;
        }

        postFacade.syncMember(event.getMember());
    }

    @KafkaListener(
            topics = MEMBER_MODIFIED_TOPIC,
            groupId = MEMBER_MODIFIED_GROUP
    )
    @Transactional
    public void handle(MemberModifiedEvent event) {
        System.out.println(
                "[Kafka] MemberModifiedEvent 수신 memberId = "
                        + event.getMember().getId()
        );
        if (postFacade.findById(event.getMember().getId()).isEmpty()) {
            return;
        }
        postFacade.syncMember(event.getMember());
    }
}
