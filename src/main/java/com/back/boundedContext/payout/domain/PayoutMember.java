package com.back.boundedContext.payout.domain;

import com.back.global.entity.BaseIdAndTime;
import com.back.shared.member.domain.ReplicaMember;
import com.back.shared.payout.dto.PayoutMemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "PAYOUT_MEMBER")
@NoArgsConstructor
public class PayoutMember extends ReplicaMember {
    public PayoutMember(int id, String username, String password, String nickname, LocalDateTime createDate, LocalDateTime modifyDate, int activityScore) {
        super(id, username, password, nickname, createDate, modifyDate, activityScore);
    }

    public PayoutMemberDto toDto() {
        return new PayoutMemberDto(
                getId(),
                getUsername(),
                getNickname(),
                getActivityScore(),
                getCreateDate(),
                getModifyDate()
        );
    }
}
