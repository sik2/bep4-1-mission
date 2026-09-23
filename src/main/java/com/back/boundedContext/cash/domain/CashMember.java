package com.back.boundedContext.cash.domain;

import java.time.LocalDateTime;

import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.member.domain.ReplicaMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "CASH_MEMBER")
public class CashMember extends ReplicaMember {
    public CashMember(int id, String nickname, String username, String password, int activityScore,
                      LocalDateTime createDate, LocalDateTime modifyDate) {
        super(id, nickname, username, password, activityScore, createDate, modifyDate);
    }

    public CashMemberDto toDto() {
        return new CashMemberDto(
                getId(),
                getCreateDate(),
                getModifyDate(),
                getUsername(),
                getNickname(),
                getActivityScore()
        );
    }
}
