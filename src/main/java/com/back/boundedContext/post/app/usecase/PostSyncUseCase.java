package com.back.boundedContext.post.app.usecase;

import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.repository.PostMemberRepository;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member를 Post 컨텍스트의 PostMember로 동기화하는 UseCase
 *
 * [설계 원칙]
 * - MemberJoinedEvent, MemberUpdatedEvent 모두 동일한 MemberDto를 사용
 * - 도메인 일관성 > 이벤트 책임분리: Member는 항상 동일한 구조(MemberDto)로 표현
 * - 신규/갱신 판단은 수신 컨텍스트(Post)의 책임
 *
 * [Post 컨텍스트 특징]
 * - PostMember 생성 시 별도 이벤트 발행 없음 (후속 동작 불필요)
 * - JPA merge를 통해 신규/갱신 모두 동일하게 처리
 */
@Service
@RequiredArgsConstructor
public class PostSyncUseCase {

    private final PostMemberRepository postMemberRepository;

    /**
     * Member를 PostMember로 동기화한다.
     *
     * @param member MemberDto - 도메인 기반 공유 DTO
     * @return 동기화된 PostMember
     */
    @Transactional
    public PostMember syncMember(MemberDto member) {
        // 민감정보인 Password는 미러링에 넘기지 않는다
        PostMember postMember = new PostMember(
                member.getId(),
                member.getCreatedAt(),
                member.getUpdatedAt(),
                member.getUsername(),
                "",
                member.getNickname(),
                member.getActivityScore()
        );
        return postMemberRepository.save(postMember);
    }

}
