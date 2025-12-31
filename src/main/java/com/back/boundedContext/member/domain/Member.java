package com.back.boundedContext.member.domain;


import com.back.shared.member.domain.SourceMember;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MemberUpdatedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 초기화 클래스
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name="MEMBER_MEMBER")
public class Member extends SourceMember {
    /**
     * 활동 점수를 증가시킨다.
     * 도메인 규칙: 점수 변경 시 다른 컨텍스트에 알리기 위해 MemberUpdatedEvent를 발행한다.
     * 
     * @param amount 증가시킬 점수
     */
    public void increasePoint(int amount) {
        if (amount == 0) return;
        setActivityScore(getActivityScore() + amount);
        // 도메인이 직접 자신의 변경을 알림 (DDD 원칙)
        publishEvent(new MemberUpdatedEvent(toDto()));
    }


    public Member(String username, String password, String nickname) {
        super(username, password, nickname);
    }


    public MemberDto toDto() {
        return new MemberDto(
                getId(),
                getCreatedAt(),
                getUpdatedAt(),
                getUsername(),
                getNickname(),
                getActivityScore()
        );
    }


}
