package com.antonialucianapires.taxi_online.domain;

import com.antonialucianapires.taxi_online.domain.exception.AccountAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignupUseCase {
    private final AccountRepository accountRepository;

    public Account execute(Account account) {
        accountRepository.findById(account.getAccountId())
            .ifPresent(existingAccount -> {
                throw new AccountAlreadyExistsException(account.getEmail().getValue().get());
            });
        return accountRepository.save(account);
    }
}
