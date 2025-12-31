package com.back.boundedContext.member.domain;

import com.back.shared.member.domain.SourceMember;
import com.back.shared.member.dto.MemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER_MEMBER")
@NoArgsConstructor
@Getter
public class Member extends SourceMember {

    public Member(String username, String password, String nickname) {
        super(username, password, nickname);
    }

    public MemberDto toDto() {
        return new MemberDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                getUsername(),
                getNickname(),
                "",
                getActivityScore()
        );
    }

    public void modify(String username, String password, String nickname) {
        if (username != null && !username.isEmpty()) {
            this.setUsername(username);
        }

        if (password != null && !password.isEmpty()) {
            this.setPassword(password);
        }

        if (nickname != null && !nickname.isEmpty()) {
            this.setNickname(nickname);
        }
    }

    public int increaseActivityScore(int amount) {
        setActivityScore(getActivityScore() + amount);

        return getActivityScore();
    }
}
