package com.back.boundedContext.post.domain;

import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name="POST_MEMBER")
@Getter
public class PostMember extends ReplicaMember {
    public PostMember(Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                      String username, String password, String nickname, int activityScore) {
        super(id, createdAt, updatedAt, username, password, nickname, activityScore);
    }
}
