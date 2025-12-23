package com.back.boundedContext.member.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Member extends BaseIdAndTime {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private int score = 0;

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public void increaseScore(int plus){
        this.score += plus;
    }
}
