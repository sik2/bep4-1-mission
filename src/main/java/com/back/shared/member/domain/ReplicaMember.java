package com.back.shared.member.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class ReplicaMember extends BaseMember {
    @Id
    private int id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public ReplicaMember(String username, String password, String nickname) {
        super(username, password, nickname);
    }

    public ReplicaMember(int id, String nickname, String username, String password,
                         int activityScore, LocalDateTime createDate, LocalDateTime modifyDate) {
        this.id = id;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        super(username, nickname, password, activityScore);
    }
}
