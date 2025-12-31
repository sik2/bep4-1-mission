package com.back.boundedContext.cash.in;


import com.back.boundedContext.cash.app.CashFacade;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.shared.cash.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cash/wallets")
@RequiredArgsConstructor
public class ApiV1WalletController {
    private final CashFacade cashFacade;

    @GetMapping("/by-member/{memberId}")
    @Transactional(readOnly = true)
    public WalletDto getItemByMember(
            @PathVariable int memberId
    ) {
        return cashFacade
                .findWalletByMemberId(memberId)
                .map(Wallet::toDto)
                .get();
    }
}