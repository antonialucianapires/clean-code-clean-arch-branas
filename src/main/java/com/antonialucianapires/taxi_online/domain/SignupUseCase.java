package com.antonialucianapires.taxi_online.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignupUseCase {
    private final AccountRepository accountRepository;

    public Account execute(Account account) {
        return accountRepository.save(account);
    }
}
