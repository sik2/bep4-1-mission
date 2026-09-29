package com.back.boundedContext.member.out;

import com.back.shared.member.event.MemberJoinedEvent;
import com.back.shared.member.event.MemberModifiedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class MemberKafkaEventPublisher {
    private static final String MEMBER_JOINED_TOPIC = "MemberJoinedEvent";
    private static final String MEMBER_MODIFIED_TOPIC = "MemberModifiedEvent";

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handle(MemberJoinedEvent event) {
        kafkaTemplate.send(MEMBER_JOINED_TOPIC, event);
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handle(MemberModifiedEvent event) {
        kafkaTemplate.send(MEMBER_MODIFIED_TOPIC, event);
    }
}
