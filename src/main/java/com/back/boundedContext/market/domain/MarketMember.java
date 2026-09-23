package com.back.boundedContext.market.domain;

import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "MARKET_MARKET_MEMBER")
@NoArgsConstructor
public class MarketMember extends ReplicaMember {
    public MarketMember(int id, String username, String password, String nickname, LocalDateTime createDate, LocalDateTime modifyDate, int activityScore) {
        super(id, username, password, nickname, createDate, modifyDate, activityScore);
    }

    public MarketMemberDto toDto() {
        return new MarketMemberDto(
                getId(),
                getUsername(),
                getNickname(),
                getActivityScore(),
                getCreateDate(),
                getModifyDate()
        );
    }
}
