package com.back.boundedContext.cash.domain;

import com.back.shared.cash.dto.CashMemberDto;
import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name="CASH_MEMBER")
@Getter
public class CashMember extends ReplicaMember {
    public CashMember(Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                      String username, String password, String nickname, int activityScore) {
        super(id, createdAt, updatedAt, username, password, nickname, activityScore);
    }

    public CashMemberDto toDto() {
        return new CashMemberDto(
                getId(),
                getCreatedAt(),
                getUpdatedAt(),
                getUsername(),
                getNickname(),
                getActivityScore()
        );
    }
}
