package com.back.boundedContext.post.domain;

import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "POST_MEMBER")
@Getter
@NoArgsConstructor
public class PostMember extends ReplicaMember {
    public PostMember(int id, String username, String password, String nickname, LocalDateTime createDate, LocalDateTime modifyDate, int activityScore) {
        super(id, username, password, nickname, createDate, modifyDate, activityScore);
    }
}
