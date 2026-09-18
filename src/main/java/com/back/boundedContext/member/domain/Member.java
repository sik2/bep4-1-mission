package com.back.boundedContext.member.domain;

import com.back.boundedContext.member.dto.MemberDto;
import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER_MEMBER")
@NoArgsConstructor
@Getter
public class Member extends SourceMember {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private int activityScore;

    public Member(String username, String password, String nickname) {
        super(username, password, nickname);
    }

    public int increaseActivityScore(int amount) {
        if (amount == 0) return getActivityScore();
        setActivityScore(getActivityScore() + amount);
        publishEvent(
                new MemberModifiedEvent(new MemberDto(this))
        );
        return getActivityScore();
    }
}
