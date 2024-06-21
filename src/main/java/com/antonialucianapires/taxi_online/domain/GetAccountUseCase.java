package com.antonialucianapires.taxi_online.domain;

import java.util.UUID;

import com.antonialucianapires.taxi_online.domain.exception.AccountNotFoundException;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class GetAccountUseCase {
    private final AccountRepository accountRepository;

    public Account execute(UUID id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));
    }
}
