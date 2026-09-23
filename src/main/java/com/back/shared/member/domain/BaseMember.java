package com.back.shared.member.domain;

import com.back.global.jpa.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class BaseMember extends BaseEntity {
    @Column(unique = true)
    protected String username;
    protected String password;
    protected String nickname;
    protected int activityScore;

    public BaseMember(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public BaseMember(String username, String nickname, String password, int activityScore) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.activityScore = activityScore;
    }

    public boolean isSystem() {
        return "system".equals(username);
    }
}
