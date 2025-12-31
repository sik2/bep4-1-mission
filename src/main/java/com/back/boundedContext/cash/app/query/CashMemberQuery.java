package com.back.boundedContext.cash.app.query;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.out.repository.CashMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



@Component
@RequiredArgsConstructor
public class CashMemberQuery {

    private final CashMemberRepository cashMemberRepository;

    @Transactional
    public long count() {
        return cashMemberRepository.count();
    }


    public Optional<CashMember> findCashMemberById(Long id) {
        return cashMemberRepository.findById(id);
    }


    @Transactional(readOnly = true)
    public Optional<CashMember> findCashMemberByUsername(String username) {
        return cashMemberRepository.findByUsername(username);
    }


}
