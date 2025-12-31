package com.back.shared.member.domain;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class ReplicaMember extends BaseMember {
    @Id
    private Long id; // id를 auto-generate하지 않고 명시적으로 생성한다.
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReplicaMember
            (Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
             String username, String password, String nickname, int activityScore)
    {
        super(username, password, nickname);
        // 생성자에서 파라미터로 받은 id, 생성시간, 최종수정시간을 그대로 넣어줌으로서 명시적으로 처리한다.
        // MemberMember의 값을 파라미터에 넣으면 그 값 그대로 싱크된다.
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.setActivityScore(activityScore);
    }
}