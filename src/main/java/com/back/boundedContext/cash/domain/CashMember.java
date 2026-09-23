package com.back.boundedContext.cash.domain;

import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "CASH_MEMBER")
@NoArgsConstructor
public class CashMember extends ReplicaMember {
        public CashMember(int id, String username, String password, String nickname, LocalDateTime createDate, LocalDateTime modifyDate, int activityScore) {
        super(id, username, password, nickname, createDate, modifyDate, activityScore);
    }

    public CashMemberDto toDto() {
        return new CashMemberDto(
                getId(),
                getUsername(),
                getNickname(),
                getActivityScore(),
                getCreateDate(),
                getModifyDate()
        );
    }
}
