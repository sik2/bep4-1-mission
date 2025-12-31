package com.back.boundedContext.post.domain;

import java.time.LocalDateTime;

import com.back.shared.member.domain.ReplicaMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POST_MEMBER")
@Getter
@NoArgsConstructor
public class PostMember extends ReplicaMember {
	public PostMember(int id, LocalDateTime createTime, LocalDateTime modifyTime, String userName, String password, String nickName, int activityScore) {
		super(id, createTime, modifyTime, userName, password, nickName, activityScore);
	}
}
