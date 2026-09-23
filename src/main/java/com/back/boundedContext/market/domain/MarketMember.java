package com.back.boundedContext.market.domain;

import java.time.LocalDateTime;

import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.domain.ReplicaMember;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "MARKET_MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketMember extends ReplicaMember {
    public MarketMember(int id, String username, String nickname, String password, int activityScore,
                      LocalDateTime createDate, LocalDateTime modifyDate) {
        super(id, nickname, username, password, activityScore, createDate, modifyDate);
    }

    public MarketMemberDto toDto() {
        return new MarketMemberDto(getId(), username, nickname, "", activityScore,
                                   getCreateDate(), getModifyDate());
    }
}
