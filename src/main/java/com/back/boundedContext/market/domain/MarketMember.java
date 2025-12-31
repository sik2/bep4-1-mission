package com.back.boundedContext.market.domain;

import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name="MARKET_MEMBER")
@Getter
public class MarketMember extends ReplicaMember {
    public MarketMember(Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                      String username, String password, String nickname, int activityScore) {
        super(id, createdAt, updatedAt, username, password, nickname, activityScore);
    }

    public MarketMemberDto toDto() {
        return new MarketMemberDto(
                getId(),
                getCreatedAt(),
                getUpdatedAt(),
                getUsername(),
                getNickname(),
                getActivityScore()
        );
    }
}

