package com.back.boundedContext.post.domain;

import java.time.LocalDateTime;

import com.back.shared.member.domain.ReplicaMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_MEMBER")
@NoArgsConstructor
@Getter
public class PostMember extends ReplicaMember {
    public PostMember(String username, String password, String nickname) {
        super(username, password, nickname);
    }

    public PostMember(int id, String nickname, String username, String password, int activityScore,
                      LocalDateTime createDate, LocalDateTime modifyDate) {
        super(id, nickname, username, password, activityScore, createDate, modifyDate);
    }
}