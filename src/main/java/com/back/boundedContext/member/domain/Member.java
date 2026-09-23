package com.back.boundedContext.member.domain;

import com.back.shared.member.domain.SourceMember;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MemberModifiedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.back.global.GlobalConfig.eventPublisher;


@Getter
@Entity
@Table(name = "MEMBER_MEMBER")
@NoArgsConstructor
public class Member extends SourceMember {
    public Member(String username, String password, String nickname) {
        super(username, password, nickname);
    }

    public int increaseActivityScore(int amount) {
        if (amount == 0) return getActivityScore();

        setActivityScore(getActivityScore() + amount);
        eventPublisher.publish(new MemberModifiedEvent(toDto()));

        return getActivityScore();
    }

    public MemberDto toDto() {
        return new MemberDto(
                getId(),
                getUsername(),
                getNickname(),
                getActivityScore(),
                getCreateDate(),
                getModifyDate()
        );
    }
}